package com.teamflow.forestory_be.story.chapter.domain.entity;

import com.teamflow.forestory_be.story.chapter.domain.exception.InvalidChapterOwnerException;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterBody;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterSubtitle;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterTitle;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import java.time.LocalDate;
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
    private final String thumbnailUrl;
    private final long likeCount;
    private final ChapterStatus status;
    private final int chapterNumber;
    private final LocalDate publishedAt;

    private Chapter(Long id, Long seriesId, Long authorId,
                    ChapterTitle title, ChapterSubtitle subtitle, ChapterBody body, String thumbnailUrl,
                    long likeCount, ChapterStatus status, int chapterNumber, LocalDate publishedAt) {
        this.id = Objects.requireNonNull(id);
        this.seriesId = Objects.requireNonNull(seriesId);
        this.authorId = Objects.requireNonNull(authorId);
        this.title = Objects.requireNonNull(title);
        this.subtitle = Objects.requireNonNull(subtitle);
        this.body = Objects.requireNonNull(body);
        this.thumbnailUrl = thumbnailUrl;
        this.likeCount = likeCount;
        this.status = Objects.requireNonNull(status);
        this.chapterNumber = chapterNumber;
        this.publishedAt = (status == ChapterStatus.PUBLISHED)
                ? (publishedAt != null ? publishedAt : LocalDate.now())
                : null;
    }

    public static Chapter reconstruct(Long id, Long seriesId, Long authorId,
                                      ChapterTitle title, ChapterSubtitle subtitle,
                                      ChapterBody body, String thumbnailUrl,
                                      long likeCount, ChapterStatus status,
                                      int chapterNumber, LocalDate publishedAt) {
        return new Chapter(id, seriesId, authorId, title, subtitle, body, thumbnailUrl,
                likeCount, status, chapterNumber, publishedAt);
    }

    public static Chapter createPublished(Long seriesId, Long authorId,
                                          ChapterTitle title, ChapterSubtitle subtitle,
                                          ChapterBody body, String thumbnailUrl,
                                          int chapterNumber) {
        Long id = TsidGenerator.generate();
        return new Chapter(id, seriesId, authorId, title, subtitle, body, thumbnailUrl,
                0L, ChapterStatus.PUBLISHED, chapterNumber, LocalDate.now());
    }

    public static Chapter createDraft(Long seriesId, Long authorId,
                                      ChapterTitle title, ChapterSubtitle subtitle,
                                      ChapterBody body, String thumbnailUrl,
                                      int chapterNumber) {
        Long id = TsidGenerator.generate();
        return new Chapter(id, seriesId, authorId, title, subtitle, body, thumbnailUrl,
                0L, ChapterStatus.DRAFT, chapterNumber, null);
    }

    public Chapter update(ChapterTitle title, ChapterSubtitle subtitle, ChapterBody body,
                          String thumbnailUrl, ChapterStatus status, int chapterNumber) {
        LocalDate nextPublishedAt = (status == ChapterStatus.PUBLISHED)
                ? (this.publishedAt != null ? this.publishedAt : LocalDate.now())
                : null;

        return new Chapter(
                this.id,
                this.seriesId,
                this.authorId,
                title,
                subtitle,
                body,
                thumbnailUrl,
                this.likeCount,
                status,
                chapterNumber,
                nextPublishedAt
        );
    }

    public void validateOwnerOrThrow(Long userId) {
        if (!Objects.equals(authorId, userId)) {
            throw new InvalidChapterOwnerException();
        }
    }
}
