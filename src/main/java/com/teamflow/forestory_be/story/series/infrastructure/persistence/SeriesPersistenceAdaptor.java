package com.teamflow.forestory_be.story.series.infrastructure.persistence;

import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.story.series.domain.exception.SeriesNotFoundException;
import com.teamflow.forestory_be.story.series.domain.repository.SeriesRepositoryPort;
import com.teamflow.forestory_be.story.series.infrastructure.persistence.entity.SeriesJpaEntity;
import com.teamflow.forestory_be.story.series.infrastructure.persistence.repository.SeriesJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeriesPersistenceAdaptor implements SeriesRepositoryPort {

    private final SeriesJpaRepository seriesJpaRepository;

    @Override
    public void save(Series series) {
        SeriesJpaEntity seriesJpaEntity = SeriesPersistenceMapper.toJpaEntity(series);
        seriesJpaRepository.save(seriesJpaEntity);
    }

    @Override
    public Series getById(Long id) {
        SeriesJpaEntity seriesJpaEntity = seriesJpaRepository.findById(id)
                .orElseThrow(SeriesNotFoundException::new);
        return SeriesPersistenceMapper.toDomainEntity(seriesJpaEntity);

    }

    @Override
    public void deleteById(Long id) { seriesJpaRepository.deleteById(id); }
}
