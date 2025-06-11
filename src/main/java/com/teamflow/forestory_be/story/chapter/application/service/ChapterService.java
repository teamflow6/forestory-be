package com.teamflow.forestory_be.story.chapter.application.service;

import com.teamflow.forestory_be.story.chapter.application.dto.command.CreateChapterCommand;
import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.domain.repository.ChapterRepositoryPort;
import com.teamflow.forestory_be.story.chapter.domain.vo.*;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.CreateChapterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepositoryPort chapterRepositoryPort;

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
        List<String> imageUrls = command.imageUrls();
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
}

