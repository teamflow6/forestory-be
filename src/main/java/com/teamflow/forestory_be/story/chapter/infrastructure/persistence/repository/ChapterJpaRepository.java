package com.teamflow.forestory_be.story.chapter.infrastructure.persistence.repository;

import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterJpaRepository extends JpaRepository<ChapterJpaEntity, Long>, ChapterQueryRepository {
    Optional<ChapterJpaEntity> findTopBySeriesIdOrderByChapterNumberDesc(Long seriesId);

}
