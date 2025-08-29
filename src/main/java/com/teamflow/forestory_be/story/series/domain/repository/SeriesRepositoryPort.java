package com.teamflow.forestory_be.story.series.domain.repository;

import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import com.teamflow.forestory_be.story.series.presentation.dto.response.GetSeriesListResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface SeriesRepositoryPort {

    void save(Series series);

    Series getById(Long id);

    void deleteById(Long id);

    List<GetSeriesListResponse> findByUserId(Long userId, Integer lastSeriesNumber, int size);

    List<GetSeriesListResponse> findByUserIdAndType(Long userId, Type type, Integer lastSeriesNumber, int size);

    List<Object[]> findTopByWeeklyLikesRaw(Type type, int limit, LocalDateTime since, LocalDateTime until);
    List<Object[]> findWeeklyPopularAfterCursorById(Type type, int size, LocalDateTime since, LocalDateTime until, Long cursorId);
    List<Object[]> findLatestAfterCursorById(Type type, int size, LocalDateTime since, LocalDateTime until, Long cursorId);

}
