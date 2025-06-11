package com.teamflow.forestory_be.story.series.infrastructure.persistence.repository;

import com.teamflow.forestory_be.story.series.infrastructure.persistence.entity.SeriesJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesJpaRepository extends JpaRepository<SeriesJpaEntity, Long> {
}
