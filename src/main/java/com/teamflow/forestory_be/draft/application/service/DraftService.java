package com.teamflow.forestory_be.draft.application.service;

import com.teamflow.forestory_be.article.infrastructure.persistence.ArticlePersistenceAdaptor;
import com.teamflow.forestory_be.article.infrastructure.persistence.entity.ArticleJpaEntity;
import com.teamflow.forestory_be.draft.application.dto.command.BulkDeleteDraftCommand;
import com.teamflow.forestory_be.draft.application.dto.query.GetDraftsQuery;
import com.teamflow.forestory_be.draft.domain.vo.DraftScope;
import com.teamflow.forestory_be.draft.infrastructure.repository.DraftReadPort;
import com.teamflow.forestory_be.draft.presentation.dto.response.BulkDeleteDraftResponse;
import com.teamflow.forestory_be.draft.presentation.dto.response.DraftListItem;
import com.teamflow.forestory_be.draft.presentation.dto.response.GetDraftResponse;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.ChapterPersistenceAdaptor;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DraftService {

    private final DraftReadPort port;
    private final ArticlePersistenceAdaptor articleWritePort;
    private final ChapterPersistenceAdaptor chapterWritePort;

    public GetDraftResponse getDrafts(GetDraftsQuery q) {
        int limit = Math.max(1, q.size());
        int limitPlusOne = limit + 1;
        LocalDateTime cursor = q.cursorCreatedAt();
        DraftScope scope = q.draftScope();

        List<DraftListItem> items;

        switch (scope) {
            case ARTICLE -> {
                List<ArticleJpaEntity> a = port.sliceArticles(q.userId(), cursor, limitPlusOne);
                items = mapArticles(a);
            }
            case NOVEL -> {
                List<ChapterJpaEntity> c = port.sliceChaptersBySeriesType(q.userId(), "NOVEL", cursor, limitPlusOne);
                items = mapChapters(c, DraftScope.NOVEL);
            }
            case ESSAY -> {
                List<ChapterJpaEntity> c = port.sliceChaptersBySeriesType(q.userId(), "ESSAY", cursor, limitPlusOne);
                items = mapChapters(c, DraftScope.ESSAY);
            }
            case ALL -> {
                List<DraftListItem> merged = new ArrayList<>();
                merged.addAll(mapArticles(port.sliceArticles(q.userId(), cursor, limitPlusOne)));
                merged.addAll(mapChapters(port.sliceChaptersBySeriesType(q.userId(), "NOVEL", cursor, limitPlusOne), DraftScope.NOVEL));
                merged.addAll(mapChapters(port.sliceChaptersBySeriesType(q.userId(), "ESSAY", cursor, limitPlusOne), DraftScope.ESSAY));
                merged.sort(Comparator.comparing(DraftListItem::createdAt).reversed());
                items = merged.size() > limitPlusOne ? merged.subList(0, limitPlusOne) : merged;
            }
            default -> items = List.of();
        }
        boolean hasNext = items.size() > limit;
        List<DraftListItem> page = hasNext ? items.subList(0, limit) : items;
        LocalDateTime nextCursor = page.isEmpty() ? null : page.get(page.size() - 1).createdAt();

        return new GetDraftResponse(page, nextCursor, hasNext);
    }

    private List<DraftListItem> mapArticles(List<ArticleJpaEntity> rows) {
        List<DraftListItem> out = new ArrayList<>(rows.size());
        for (ArticleJpaEntity a : rows) {
            out.add(new DraftListItem(
                    a.getId() == null ? null : a.getId().toString(),
                    a.getTitle(),
                    a.getSubtitle(),
                    null,                     // ARTICLE → chapterNumber 없음
                    DraftScope.ARTICLE,
                    a.getCreatedAt()
            ));
        }
        return out;
    }

    private List<DraftListItem> mapChapters(List<ChapterJpaEntity> rows, DraftScope scope) {
        List<DraftListItem> out = new ArrayList<>(rows.size());
        for (ChapterJpaEntity c : rows) {
            out.add(new DraftListItem(
                    c.getId() == null ? null : c.getId().toString(),
                    c.getTitle(),
                    c.getSubtitle(),
                    c.getChapterNumber(),     // 챕터만 값 있음
                    scope,                    // NOVEL/ESSAY 구분
                    c.getCreatedAt()
            ));
        }
        return out;
    }
    @Transactional
    public BulkDeleteDraftResponse bulkDelete(BulkDeleteDraftCommand command) {
        Map<DraftScope, List<Long>> idsByScope = command.items().stream()
                .filter(i -> i.scope() != null && i.targetId() != null)
                .filter(i -> i.scope() != DraftScope.ALL)
                .collect(Collectors.groupingBy(
                        BulkDeleteDraftCommand.Item::scope,
                        () -> new EnumMap<>(DraftScope.class),
                        Collectors.mapping(BulkDeleteDraftCommand.Item::targetId, Collectors.toList())
                ));

        int totalDeleted = 0;

        for (Map.Entry<DraftScope, List<Long>> e : idsByScope.entrySet()) {
            DraftScope scope = e.getKey();
            List<Long> ids = e.getValue();
            if (ids.isEmpty()) continue;

            switch (scope) {
                case ARTICLE -> {
                    totalDeleted += articleWritePort.deleteArticles(command.userId(), ids);
                }
                case NOVEL -> {

                    Type seriesType = Type.NOVEL;
                    totalDeleted += chapterWritePort.deleteChaptersBySeriesType(command.userId(), seriesType, ids);
                }
                case ESSAY -> {
                    Type seriesType = Type.ESSAY;
                    totalDeleted += chapterWritePort.deleteChaptersBySeriesType(command.userId(), seriesType, ids);
                }
                default -> { /* ALL은 제외됨 */ }
            }
        }
        return new BulkDeleteDraftResponse(totalDeleted);
    }
}


