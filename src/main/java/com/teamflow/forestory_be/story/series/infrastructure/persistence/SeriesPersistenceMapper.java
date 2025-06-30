package com.teamflow.forestory_be.story.series.infrastructure.persistence;

import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesIntroduction;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesTitle;
import com.teamflow.forestory_be.story.series.infrastructure.persistence.entity.SeriesJpaEntity;

public class SeriesPersistenceMapper {

    private SeriesPersistenceMapper() {
    }

    public static Series toDomainEntity(SeriesJpaEntity entity) {
        return Series.reconstruct(
                entity.getId(),
                entity.getAuthorId(),
                new SeriesTitle(entity.getTitle()),
                new SeriesIntroduction(entity.getIntroduction()),
                entity.getThumbnailUrl(),
                entity.getType(),
                entity.getSeriesStatus()
        );
    }

    public static SeriesJpaEntity toJpaEntity(Series series) {
        return SeriesJpaEntity.builder()
                .id(series.getId())
                .authorId(series.getAuthorId())
                .title(series.getTitle().value())
                .introduction(series.getIntroduction().value())
                .thumbnailUrl(series.getThumbnailUrl())
                .type(series.getType())
                .seriesStatus(series.getSeriesStatus())
                .build();
    }
}
