package com.teamflow.forestory_be.story.chapter.domain.entity;

import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterBody;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterSubtitle;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterTitle;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Chapter {
    private final Long id;
    private final Long seriesId;
    private final Long authorId;
    private final ChapterTitle title;
    private final ChapterSubtitle subtitle;
    private final ChapterBody body;
    private final ChapterStatus status;
    private final List<String> imageUrls;
    private final String chapterNumber;

    private static final int MIN_DIGIT_LENGTH = 2;

    private Chapter(Long id, Long seriesId, Long authorId, ChapterTitle title,
                    ChapterSubtitle subtitle, ChapterBody body, List<String> imageUrls,
                    ChapterStatus status, String chapterNumber) {
        this.id = Objects.requireNonNull(id);
        this.seriesId = Objects.requireNonNull(seriesId);
        this.authorId = Objects.requireNonNull(authorId);
        this.title = Objects.requireNonNull(title);
        this.subtitle = Objects.requireNonNull(subtitle);
        this.body = Objects.requireNonNull(body);
        this.imageUrls = imageUrls != null ? List.copyOf(imageUrls) : List.of();
        this.status = Objects.requireNonNull(status);
        this.chapterNumber = chapterNumber;
    }

    public static Chapter reconstruct(Long id, Long seriesId, Long authorId,
                                      ChapterTitle title, ChapterSubtitle subtitle,
                                      ChapterBody body, List<String> imageUrls,
                                      ChapterStatus status, String chapterNumber) {
        return new Chapter(id, seriesId, authorId, title, subtitle, body, imageUrls,
                status, chapterNumber);
    }

    public static Chapter createPublished(Long seriesId, Long authorId,
                                          ChapterTitle title, ChapterSubtitle subtitle,
                                          ChapterBody body, List<String> imageUrls,
                                          String chapterNumber) {
        Long id = TsidGenerator.generate();
        return new Chapter(id, seriesId, authorId, title, subtitle, body, imageUrls,
                ChapterStatus.PUBLISHED, chapterNumber);
    }

    public static Chapter createDraft(Long seriesId, Long authorId,
                                      ChapterTitle title, ChapterSubtitle subtitle,
                                      ChapterBody body, List<String> imageUrls,
                                      String chapterNumber) {
        Long id = TsidGenerator.generate();
        return new Chapter(id, seriesId, authorId, title, subtitle, body, imageUrls,
                ChapterStatus.DRAFT, chapterNumber);
    }

    public static String generateNext(String lastChapterNumber) {
        int next = Integer.parseInt(lastChapterNumber) + 1;
        int digitLength = Math.max(MIN_DIGIT_LENGTH, String.valueOf(next).length());
        return String.format("%0" + digitLength + "d", next);
    }


}
