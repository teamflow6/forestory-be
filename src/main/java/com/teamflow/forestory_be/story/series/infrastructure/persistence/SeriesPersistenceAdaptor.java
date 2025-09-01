package com.teamflow.forestory_be.story.series.infrastructure.persistence;

import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.story.series.domain.exception.SeriesNotFoundException;
import com.teamflow.forestory_be.story.series.domain.repository.SeriesRepositoryPort;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import com.teamflow.forestory_be.story.series.infrastructure.persistence.entity.SeriesJpaEntity;
import com.teamflow.forestory_be.story.series.infrastructure.persistence.repository.SeriesJpaRepository;
import com.teamflow.forestory_be.story.series.presentation.dto.response.GetSeriesListResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public void deleteById(Long id) {
        seriesJpaRepository.deleteById(id);
    }

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
    @Transactional(readOnly = true)
    public List<GetSeriesListResponse> findByAuthorAndStatus(
            Long authorId, SeriesStatus status, LocalDateTime lastCreatedAt, int size) {

        Pageable pageable = PageRequest.of(0, Math.max(1, size));
        return seriesJpaRepository.findByAuthorAndStatus(authorId, status, lastCreatedAt, pageable)
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
    @Transactional(readOnly = true)
    public List<GetSeriesListResponse> findByAuthorAndTypeAndStatus(
            Long authorId, Type type, SeriesStatus status, LocalDateTime lastCreatedAt, int size) {

        Pageable pageable = PageRequest.of(0, Math.max(1, size));
        return seriesJpaRepository.findByAuthorAndTypeAndStatus(authorId, type, status, lastCreatedAt, pageable)
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

    @Transactional
    public void publishIfPending(Long seriesId) {
        SeriesJpaEntity e = seriesJpaRepository.findById(seriesId)
                .orElseThrow(SeriesNotFoundException::new);
        e.publishIfPending();                   // PENDING일 때만 PUBLISHED로
    }



    /* ========= 추가: 홈 섹션 & 탭 목록용 ========= */

    /** 홈 섹션: 지난 주간 좋아요 Top N (조회수 제외) */
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findTopByWeeklyLikesRaw(Type type, int limit, LocalDateTime since, LocalDateTime until) {
        return seriesJpaRepository.topByWeeklyLikes(
                type,
                likeTypeOf(type),
                since, until,
                PageRequest.of(0, Math.max(1, limit))
        );
    }

    /** 탭 목록(인기): 주간 좋아요 기반 커서 페이징 */
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findWeeklyPopularAfterCursorById(
            Type type, int size, LocalDateTime since, LocalDateTime until, Long cursorId) {

        Pageable p = PageRequest.of(0, Math.max(1, size));
        TargetType likeType = likeTypeOf(type);

        if (cursorId == null) {
            return seriesJpaRepository.weeklyPopularFirst(type, likeType, since, until, p);
        }
        return seriesJpaRepository.weeklyPopularAfter(type, likeType, since, until, cursorId, p);
    }

    /** 탭 목록(최신): createdAt 기준 커서 페이징 */
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findLatestAfterCursorById(
            Type type, int size, LocalDateTime since, LocalDateTime until, Long cursorId) {

        Pageable p = PageRequest.of(0, Math.max(1, size));
        TargetType likeType = likeTypeOf(type);

        if (cursorId == null) {
            return seriesJpaRepository.latestFirst(type, likeType, since, until, p);
        }
        return seriesJpaRepository.latestAfter(type, likeType, since, until, cursorId, p);
    }

    /* NOVEL -> TargetType.NOVEL, ESSAY -> TargetType.ESSAY */
    private TargetType likeTypeOf(Type type) {
        return (type == Type.NOVEL) ? TargetType.NOVEL : TargetType.ESSAY;
    }
}
