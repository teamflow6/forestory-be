package com.teamflow.forestory_be.story.chapter.infrastructure.persisetence.repository;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.infrastructure.persisetence.entity.ChapterJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterJpaRepository extends JpaRepository<ChapterJpaEntity, Long> {
    Optional<ChapterJpaEntity> findTopBySeriesIdOrderByChapterNumberDesc(Long seriesId);

}
