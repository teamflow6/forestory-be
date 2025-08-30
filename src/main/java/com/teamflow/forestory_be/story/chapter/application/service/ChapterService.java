package com.teamflow.forestory_be.story.chapter.application.service;

import com.teamflow.forestory_be.article.domain.repository.ArticleRepositoryPort;
import com.teamflow.forestory_be.story.chapter.application.dto.command.CreateChapterCommand;
import com.teamflow.forestory_be.story.chapter.application.dto.command.DeleteChapterCommand;
import com.teamflow.forestory_be.story.chapter.application.dto.command.UpdateChapterCommand;
import com.teamflow.forestory_be.story.chapter.application.dto.query.GetChapterQuery;
import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.domain.exception.InvalidChapterOwnerException;
import com.teamflow.forestory_be.story.chapter.domain.repository.ChapterRepositoryPort;
import com.teamflow.forestory_be.story.chapter.domain.vo.*;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.ChapterDetailResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.CreateChapterResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.DeleteChapterResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.GetChapterResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.NeighborChapter;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.RandomAuthorContentResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.UpdateChapterResponse;
import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.story.series.domain.repository.SeriesRepositoryPort;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.AuthorResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.DeleteLatestChapterResponse;
import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.repository.UserRepositoryPort;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepositoryPort chapterRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final SeriesRepositoryPort seriesRepositoryPort;
    private final ArticleRepositoryPort articleRepositoryPort;

    @Transactional
    public CreateChapterResponse createChapter(CreateChapterCommand command) {
        Integer lastNumber = chapterRepositoryPort.findLastChapterNumber(command.seriesId());
        int nextNumber = (lastNumber == null) ? 1 : lastNumber + 1;

        ChapterTitle title = new ChapterTitle(command.chapterTitle());
        ChapterSubtitle subtitle = new ChapterSubtitle(command.chapterSubtitle());
        ChapterBody body = new ChapterBody(command.chapterBody());
        ChapterStatus status = command.chapterStatus();

        Chapter chapter;
        if (status == ChapterStatus.DRAFT) {
            chapter = Chapter.createDraft(
                    command.seriesId(), command.authorId(), title, subtitle, body, command.thumbnailUrl(), nextNumber
            );
        } else {
            chapter = Chapter.createPublished(
                    command.seriesId(), command.authorId(), title, subtitle, body, command.thumbnailUrl(), nextNumber
            );
        }

        chapterRepositoryPort.save(chapter);
        return CreateChapterResponse.of(chapter.getId(), chapter.getSeriesId(), chapter.getChapterNumber());
    }

    @Transactional(readOnly = true)
    public GetChapterResponse getChapter(GetChapterQuery query) {
        Chapter chapter = chapterRepositoryPort.getById(query.chapterId());
        Series series = seriesRepositoryPort.getById(chapter.getSeriesId());
        User author = userRepositoryPort.getById(chapter.getAuthorId());

        List<NeighborChapter> around = chapterRepositoryPort.findAroundPublishedChapters(
                chapter.getSeriesId(), chapter.getChapterNumber(), 4
        );

        boolean articlesThree = ThreadLocalRandom.current().nextBoolean();
        int wantArticles = articlesThree ? 3 : 2;
        int wantChapters = 5 - wantArticles;

        List<RandomAuthorContentResponse> picks = new ArrayList<>(5);
        picks.addAll(pickRandomArticles(author.getId(), null, wantArticles));
        picks.addAll(pickRandomChapters(author.getId(), chapter.getId(), wantChapters));

        return GetChapterResponse.of(
                ChapterDetailResponse.of(chapter, series),
                AuthorResponse.from(author),
                around,
                picks
        );
    }

    @Transactional
    public UpdateChapterResponse updateChapter(UpdateChapterCommand command) {
        Chapter existingChapter = chapterRepositoryPort.getById(command.chapterId());
        existingChapter.validateOwnerOrThrow(command.authorId());

        Chapter updatedChapter = existingChapter.update(
                new ChapterTitle(command.title()),
                new ChapterSubtitle(command.subtitle()),
                new ChapterBody(command.body()),
                command.thumbnailUrl(),
                command.status(),
                command.chapterNumber()
        );

        chapterRepositoryPort.save(updatedChapter);

        return UpdateChapterResponse.of(
                updatedChapter.getId(),
                updatedChapter.getSeriesId(),
                updatedChapter.getChapterNumber()
        );
    }

    public DeleteChapterResponse deleteChapter(DeleteChapterCommand command) {
        // 존재 여부 확인
        Chapter chapter = chapterRepositoryPort.getById(command.chapterId());


        // 권한 체크: 작성자가 아니면 삭제 불가
        if (!chapter.getAuthorId().equals(command.userId())) {
            throw new InvalidChapterOwnerException();
        }

        // 실제 삭제 처리
        chapterRepositoryPort.delete(chapter);

        return DeleteChapterResponse.of(command.chapterId());
    }

    private List<RandomAuthorContentResponse> pickRandomArticles(Long authorId, Long excludeArticleId, int want) {
        if (want <= 0) return List.of();
        return articleRepositoryPort.findRandomPublishedByAuthor(authorId, excludeArticleId, want)
                .stream()
                .map(RandomAuthorContentResponse::ofArticle)
                .toList();
    }

    private List<RandomAuthorContentResponse> pickRandomChapters(Long authorId, Long excludeChapterId, int want) {
        if (want <= 0) return List.of();
        return chapterRepositoryPort.findRandomPublishedByAuthor(authorId, excludeChapterId, want)
                .stream()
                .map(RandomAuthorContentResponse::ofChapter)
                .toList();
    }
}
