package com.teamflow.forestory_be.story.series.infrastructure.persistence;

import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.story.series.domain.exception.SeriesNotFoundException;
import com.teamflow.forestory_be.story.series.domain.repository.SeriesRepositoryPort;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import com.teamflow.forestory_be.story.series.infrastructure.persistence.entity.SeriesJpaEntity;
import com.teamflow.forestory_be.story.series.infrastructure.persistence.repository.SeriesJpaRepository;
import com.teamflow.forestory_be.story.series.presentation.dto.response.GetSeriesListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<GetSeriesListResponse> findByUserId(Long userId, Integer lastSeriesNumber, int size) {
        Pageable pageable = PageRequest.of(0, size);

        return seriesJpaRepository.findByAuthorId(userId, lastSeriesNumber, pageable)
                .stream()
                .map(e -> new GetSeriesListResponse(
                        e.getId().toString(),
                        e.getTitle(),
                        e.getIntroduction(),
                        e.getThumbnailUrl(),
                        e.getCreatedAt().toString(),
                        e.getType().name(),
                        e.getSeriesStatus().toString()
                ))
                .toList();
    }

    @Override
    public List<GetSeriesListResponse> findByUserIdAndType(Long userId, Type type, Integer lastSeriesNumber, int size) {
        Pageable pageable = PageRequest.of(0, size);

        return seriesJpaRepository.findByAuthorIdAndType(userId, type, lastSeriesNumber, pageable)
                .stream()
                .map(e -> new GetSeriesListResponse(
                        e.getId().toString(),
                        e.getTitle(),
                        e.getIntroduction(),
                        e.getThumbnailUrl(),
                        e.getCreatedAt().toString(),
                        e.getType().name(),
                        e.getSeriesStatus().toString()
                ))
                .toList();
    }

}
