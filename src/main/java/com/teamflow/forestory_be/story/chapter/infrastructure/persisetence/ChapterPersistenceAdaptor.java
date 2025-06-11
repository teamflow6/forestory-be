package com.teamflow.forestory_be.story.chapter.infrastructure.persisetence;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.domain.repository.ChapterRepositoryPort;
import com.teamflow.forestory_be.story.chapter.infrastructure.persisetence.entity.ChapterJpaEntity;
import com.teamflow.forestory_be.story.chapter.infrastructure.persisetence.repository.ChapterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChapterPersistenceAdaptor implements ChapterRepositoryPort {

    private final ChapterJpaRepository chapterJpaRepository;

    @Override
    public void save(Chapter chapter) {
        ChapterJpaEntity chapterJpaEntity = ChapterPersistenceMapper.toJpaEntity(chapter);
        chapterJpaRepository.save(chapterJpaEntity);
    }

    @Override
    public String findLastChapterNumber(Long seriesId) {
        return chapterJpaRepository.findTopBySeriesIdOrderByChapterNumberDesc(seriesId)
                .map(ChapterPersistenceMapper::toDomainEntity)
                .map(Chapter::getChapterNumber)
                .orElse("00");
    }

}
