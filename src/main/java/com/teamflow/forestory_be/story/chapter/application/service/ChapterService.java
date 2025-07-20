package com.teamflow.forestory_be.story.chapter.application.service;

import com.teamflow.forestory_be.story.chapter.application.dto.command.CreateChapterCommand;
import com.teamflow.forestory_be.story.chapter.application.dto.query.GetChapterQuery;
import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.domain.repository.ChapterRepositoryPort;
import com.teamflow.forestory_be.story.chapter.domain.vo.*;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.ChapterDetailResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.CreateChapterResponse;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.GetChapterResponse;
import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.story.series.domain.repository.SeriesRepositoryPort;
import com.teamflow.forestory_be.story.series.presentation.dto.response.AuthorResponse;
import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.repository.UserRepositoryPort;
import java.util.Optional;
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

    @Transactional
    public CreateChapterResponse createChapter(CreateChapterCommand command) {

        String lastChapterNumber = chapterRepositoryPort.findLastChapterNumber(command.seriesId());

        String nextChapterNumber;
        if (lastChapterNumber == null) {
            nextChapterNumber = "01";
        } else {
            nextChapterNumber = Chapter.generateNext(lastChapterNumber);
        }

        ChapterTitle title = new ChapterTitle(command.chapterTitle());
        ChapterSubtitle subtitle = new ChapterSubtitle(command.chapterSubtitle());
        ChapterBody body = new ChapterBody(command.chapterBody());
        List<String> imageUrls = Optional.ofNullable(command.imageUrls()).orElse(List.of());
        ChapterStatus status = command.chapterStatus();

        Chapter chapter;
        if (status == ChapterStatus.DRAFT) {
            chapter = Chapter.createDraft(
                    command.seriesId(), command.authorId(), title, subtitle, body, imageUrls, nextChapterNumber
            );
        } else {
            chapter = Chapter.createPublished(
                    command.seriesId(), command.authorId(), title, subtitle, body, imageUrls, nextChapterNumber
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

        ChapterDetailResponse chapterDetailResponse = ChapterDetailResponse.of(chapter, series);
        AuthorResponse authorResponse = AuthorResponse.from(author);

        return GetChapterResponse.of(chapterDetailResponse, authorResponse);
    }
}

