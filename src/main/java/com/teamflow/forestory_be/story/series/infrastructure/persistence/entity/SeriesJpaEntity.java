package com.teamflow.forestory_be.story.series.infrastructure.persistence.entity;

import com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import com.teamflow.forestory_be.support.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@Table(name = "series")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SeriesJpaEntity extends BaseTimeEntity {
    @Id
    @Column(name = "series_id")
    private Long id;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "introduction", nullable = false)
    private String introduction;

    @Column(name = "thumbnail_image_url")
    private String thumbnailUrl;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "status", nullable = false)
    private SeriesStatus seriesStatus;

}
