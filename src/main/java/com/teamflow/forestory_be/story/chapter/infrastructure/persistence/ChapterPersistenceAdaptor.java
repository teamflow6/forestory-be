package com.teamflow.forestory_be.story.chapter.infrastructure.persistence;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.domain.repository.ChapterRepositoryPort;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.repository.ChapterJpaRepository;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterWithCreatedAt;
import java.util.List;
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

    @Override
    public List<ChapterWithCreatedAt> findPublishedChaptersWithScroll(Long seriesId, String sort,
                                                                      String lastChapterNumber, int size) {
        return chapterJpaRepository.findChaptersBySeriesIdWithScroll(seriesId, sort, lastChapterNumber, size);
    }

    @Override
    public Chapter getById(Long id) {
        ChapterJpaEntity entity = chapterJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chapter not found with id: " + id));
        return ChapterPersistenceMapper.toDomainEntity(entity);
    }

}