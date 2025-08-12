package com.teamflow.forestory_be.story.chapter.domain.entity;

import com.teamflow.forestory_be.story.chapter.domain.exception.InvalidChapterOwnerException;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterBody;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterSubtitle;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterTitle;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import java.time.LocalDate;
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
    private final int chapterNumber;
    // 발행 시점 (PUBLISHED일 때만 값 존재)
    private final LocalDate publishedAt;

    private Chapter(Long id, Long seriesId, Long authorId,
                    ChapterTitle title, ChapterSubtitle subtitle, ChapterBody body,
                     ChapterStatus status,
                    int chapterNumber, LocalDate publishedAt) {
        this.id = Objects.requireNonNull(id);
        this.seriesId = Objects.requireNonNull(seriesId);
        this.authorId = Objects.requireNonNull(authorId);
        this.title = Objects.requireNonNull(title);
        this.subtitle = Objects.requireNonNull(subtitle);
        this.body = Objects.requireNonNull(body);
        this.status = Objects.requireNonNull(status);
        this.chapterNumber = chapterNumber;
        // DRAFT이면 null, PUBLISHED면 값
        this.publishedAt = (status == ChapterStatus.PUBLISHED)
                ? (publishedAt != null ? publishedAt : LocalDate.now())
                : null;
    }

    /** DB 재구성용 */
    public static Chapter reconstruct(Long id, Long seriesId, Long authorId,
                                      ChapterTitle title, ChapterSubtitle subtitle,
                                      ChapterBody body,
                                      ChapterStatus status, int chapterNumber,
                                      LocalDate publishedAt) {
        return new Chapter(id, seriesId, authorId, title, subtitle, body,
                status, chapterNumber, publishedAt);
    }

    /** 발행 생성 */
    public static Chapter createPublished(Long seriesId, Long authorId,
                                          ChapterTitle title, ChapterSubtitle subtitle,
                                          ChapterBody body,
                                          int chapterNumber) {
        Long id = TsidGenerator.generate();
        return new Chapter(id, seriesId, authorId, title, subtitle, body,
                ChapterStatus.PUBLISHED, chapterNumber, LocalDate.now());
    }

    /** 초안 생성 */
    public static Chapter createDraft(Long seriesId, Long authorId,
                                      ChapterTitle title, ChapterSubtitle subtitle,
                                      ChapterBody body,
                                      int chapterNumber) {
        Long id = TsidGenerator.generate();
        return new Chapter(id, seriesId, authorId, title, subtitle, body,
                ChapterStatus.DRAFT, chapterNumber, null);
    }

    /** 수정: 상태에 따라 publishedAt 유지/세팅 */
    public Chapter update(ChapterTitle title, ChapterSubtitle subtitle, ChapterBody body,
                          ChapterStatus status, int chapterNumber) {
        LocalDate nextPublishedAt;
        if (status == ChapterStatus.PUBLISHED) {
            // 이미 발행돼 있었다면 기존 발행일 유지, 처음 발행이면 오늘로
            nextPublishedAt = (this.publishedAt != null) ? this.publishedAt : LocalDate.now();
        } else {
            // DRAFT로 바꾸면 발행일 제거 (정책에 따라 유지하고 싶다면 this.publishedAt로 변경)
            nextPublishedAt = null;
        }

        return new Chapter(
                this.id,
                this.seriesId,
                this.authorId,
                title,
                subtitle,
                body,
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
