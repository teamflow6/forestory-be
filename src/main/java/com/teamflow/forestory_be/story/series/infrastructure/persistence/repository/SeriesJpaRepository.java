package com.teamflow.forestory_be.story.series.infrastructure.persistence.repository;

import com.teamflow.forestory_be.story.series.domain.vo.Type;
import com.teamflow.forestory_be.story.series.infrastructure.persistence.entity.SeriesJpaEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeriesJpaRepository extends JpaRepository<SeriesJpaEntity, Long> {

    @Query("""
        SELECT s 
        FROM SeriesJpaEntity s
        WHERE s.authorId = :userId
        AND (:lastSeriesNumber IS NULL OR s.id < :lastSeriesNumber)
        ORDER BY s.id DESC
        """)
    List<SeriesJpaEntity> findByAuthorId(
            @Param("userId") Long userId,
            @Param("lastSeriesNumber") Integer lastSeriesNumber,
            Pageable pageable
    );

    @Query("""
        SELECT s 
        FROM SeriesJpaEntity s
        WHERE s.authorId = :userId
        AND s.type = :type
        AND (:lastSeriesNumber IS NULL OR s.id < :lastSeriesNumber)
        ORDER BY s.id DESC
        """)
    List<SeriesJpaEntity> findByAuthorIdAndType(
            @Param("userId") Long userId,
            @Param("type") Type type,
            @Param("lastSeriesNumber") Integer lastSeriesNumber,
            Pageable pageable
    );
}
