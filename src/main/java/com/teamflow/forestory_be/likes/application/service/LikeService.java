// likes/application/service/LikeService.java
package com.teamflow.forestory_be.likes.application.service;

import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.article.domain.repository.ArticleRepositoryPort;
import com.teamflow.forestory_be.draft.domain.vo.DraftScope; // ARTICLE/NOVEL/ESSAY
import com.teamflow.forestory_be.likes.application.dto.command.BulkDeleteLikeCommand;
import com.teamflow.forestory_be.likes.application.dto.command.CreateLikeCommand;
import com.teamflow.forestory_be.likes.application.dto.command.DeleteLikeCommand;
import com.teamflow.forestory_be.likes.application.dto.query.GetMyLikesQuery;
import com.teamflow.forestory_be.likes.domain.entity.Likes;
import com.teamflow.forestory_be.likes.domain.repository.LikeCounterPort;
import com.teamflow.forestory_be.likes.domain.repository.LikeRepositoryPort;
import com.teamflow.forestory_be.likes.domain.vo.LikeStatus;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import com.teamflow.forestory_be.likes.presentation.dto.response.GetMyLikesResponse;
import com.teamflow.forestory_be.likes.presentation.dto.response.LikeListItem;
import com.teamflow.forestory_be.likes.presentation.dto.response.LikeResponse;
import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.domain.repository.ChapterRepositoryPort;
import java.time.LocalDateTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepositoryPort likeRepositoryPort;
    private final LikeCounterPort likeCounterPort;
    private final ArticleRepositoryPort articleRepositoryPort;
    private final ChapterRepositoryPort chapterRepositoryPort;

    @Transactional
    public LikeResponse create(CreateLikeCommand command) {
        Likes like = likeRepositoryPort
                .findByUserAndTarget(command.userId(), command.targetType(), command.targetId())
                .orElse(null);

        if (like == null) {
            Likes saved = likeRepositoryPort.save(
                    Likes.create(command.userId(), command.targetType(), command.targetId())
            );
            likeCounterPort.increase(command.targetType(), command.targetId());
            return LikeResponse.from(saved);
        }

        if (like.isActive()) {
            return LikeResponse.from(like); // 멱등
        }

        // DELETED -> ACTIVE (새 인스턴스 반환하므로 재할당)
        like = like.activate();
        Likes saved = likeRepositoryPort.save(like);
        likeCounterPort.increase(command.targetType(), command.targetId());
        return LikeResponse.from(saved);
    }

    @Transactional
    public LikeResponse delete(DeleteLikeCommand command) {
        Likes like = likeRepositoryPort
                .findByUserAndTarget(command.userId(), command.targetType(), command.targetId())
                .orElse(null);

        if (like == null || like.isDeleted()) {
            return new LikeResponse(
                    null,
                    command.userId().toString(),
                    command.targetType(),
                    command.targetId().toString(),
                    LikeStatus.DELETED
            );
        }

        // ACTIVE -> DELETED (새 인스턴스 반환하므로 재할당)
        like = like.delete();
        Likes saved = likeRepositoryPort.save(like);
        likeCounterPort.decrease(command.targetType(), command.targetId());
        return LikeResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public GetMyLikesResponse getMyLikes(GetMyLikesQuery q) {
        int limit = Math.max(1, q.size());
        int limitPlusOne = limit + 1;

        // 1) likes 슬라이스 (updated_at DESC, like_id DESC)
        List<Likes> likeRows = likeRepositoryPort.sliceByUserUpdatedDesc(
                q.userId(), q.cursorUpdatedAt(), q.cursorLikeId(), limitPlusOne);

        if (likeRows.isEmpty()) {
            return new GetMyLikesResponse(List.of(), null, null, false);
        }

        // 2) targetType별 id 수집
        List<Long> articleIds = new ArrayList<>();
        List<Long> chapterIds = new ArrayList<>();
        for (Likes l : likeRows) {
            TargetType tt = l.getTarget();
            if (tt == TargetType.ARTICLE) {
                articleIds.add(l.getTargetId());
            } else { // NOVEL / ESSAY → 챕터에서 조회
                chapterIds.add(l.getTargetId());
            }
        }

        // 3) 대상 배치 조회(Map) - 포트는 도메인만 반환
        Map<Long, Article> articleMap =
                articleIds.isEmpty() ? Map.of() : articleRepositoryPort.findByIdsAsMap(articleIds);
        Map<Long, Chapter> chapterMap =
                chapterIds.isEmpty() ? Map.of() : chapterRepositoryPort.findByIdsAsMap(chapterIds);

        // 4) like 순서 유지하며 응답 매핑
        List<LikeListItem> all = new ArrayList<>(likeRows.size());
        for (Likes l : likeRows) {
            TargetType tt = l.getTarget();
            DraftScope scope = DraftScope.valueOf(tt.name()); // TargetType → DraftScope 매핑 (이름 동일 가정)
            Long tid = l.getTargetId();
            LocalDateTime uat = l.getUpdatedAt();

            if (tt == TargetType.ARTICLE) {
                Article a = articleMap.get(tid);
                if (a == null) continue; // 대상 삭제/권한X → skip
                all.add(new LikeListItem(
                        tid.toString(), scope, a.getTitle().toString(), a.getSubtitle().toString(), a.getThumbnailUrl(),null, uat
                ));
            } else { // NOVEL or ESSAY -> Chapter
                Chapter c = chapterMap.get(tid);
                if (c == null) continue;
                all.add(new LikeListItem(
                        tid.toString(), scope, c.getTitle().toString(), c.getSubtitle().toString(), c.getThumbnailUrl(),c.getChapterNumber(), uat
                ));
            }
        }

        boolean hasNext = all.size() > limit;
        List<LikeListItem> page = hasNext ? all.subList(0, limit) : all;

        // 5) next 커서 (likeRows 기준)
        LocalDateTime nextAt = null; Long nextId = null;
        int idx = Math.min(likeRows.size(), limit) - 1;
        if (idx >= 0) {
            nextAt = likeRows.get(idx).getUpdatedAt();
            nextId = likeRows.get(idx).getId();
        }

        return new GetMyLikesResponse(page, nextAt, nextId, hasNext);
    }

    @Transactional
    public void bulkDelete(BulkDeleteLikeCommand command) {
        if (command == null || command.targets() == null || command.targets().isEmpty()) return;

        command.targets().forEach((type, ids) -> {
            if (ids == null || ids.isEmpty()) return;
            likeRepositoryPort.deleteAllByUserIdAndTargetIdsAndTargetType(
                    command.userId(), ids, type
            );
        });
    }
}
