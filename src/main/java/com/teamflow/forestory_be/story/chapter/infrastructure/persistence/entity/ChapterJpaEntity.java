package com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity;

import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import com.teamflow.forestory_be.support.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Getter
@Entity
@Builder
@Table(name = "chapters")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChapterJpaEntity extends BaseTimeEntity {

    @Id
    @Column(name = "chapter_id")
    private Long id;

    @Column(name = "series_id", nullable = false)
    private Long seriesId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "subtitle", nullable = false)
    private String subtitle;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "thumbnailUrl")
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ChapterStatus status;

    @Column(name = "chapter_number", nullable = false, length = 10)
    private Integer chapterNumber;

    @Column(name = "published_at")
    private LocalDate publishedAt;

    @Builder.Default
    @Column(name = "like_count", nullable = false)
    private long likeCount = 0L;
}
