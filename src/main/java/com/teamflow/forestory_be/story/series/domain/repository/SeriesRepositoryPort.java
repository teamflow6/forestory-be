package com.teamflow.forestory_be.story.series.domain.repository;

import com.teamflow.forestory_be.story.series.domain.entity.Series;

public interface SeriesRepositoryPort {

    void save(Series series);

    Series getById(Long id);
}
