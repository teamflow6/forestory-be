package com.teamflow.forestory_be.story.chapter.infrastructure.persistence;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.domain.repository.ChapterRepositoryPort;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.repository.ChapterJpaRepository;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.NeighborChapter;
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
    public Integer findLastChapterNumber(Long seriesId) {
        Integer max = chapterJpaRepository.findMaxChapterNumber(seriesId);
        return (max == null || max == 0) ? null : max;
    }

    @Override
    public List<ChapterWithCreatedAt> findPublishedChaptersWithScroll(Long seriesId, String sort, int lastChapterNumber, int size) {
        return chapterJpaRepository.findChaptersBySeriesIdWithScroll(seriesId, sort, lastChapterNumber, size);
    }

    @Override
    public Chapter getById(Long id) {
        ChapterJpaEntity entity = chapterJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chapter not found with id: " + id));
        return ChapterPersistenceMapper.toDomainEntity(entity);
    }

    @Override
    public List<NeighborChapter> findAroundPublishedChapters(Long seriesId, int currentChapterNumber, int nextCount) {
        return chapterJpaRepository.findAroundPublishedChapters(
                seriesId, currentChapterNumber, nextCount
        );
    }
    @Override
    public List<Chapter> findRandomPublishedByAuthor(Long authorId, Long excludeChapterId, int limit) {
        return chapterJpaRepository.pickRandomByAuthor(authorId, excludeChapterId, limit)
                .stream()
                .map(ChapterPersistenceMapper::toDomainEntity)
                .toList();
    }

    @Override
    public void deleteAllBySeriesId(Long seriesId) {
        chapterJpaRepository.deleteBySeriesId(seriesId);
    }

    @Override
    public Chapter findTopBySeriesIdOrderByChapterNumberDesc(Long seriesId) {
        ChapterJpaEntity entity = chapterJpaRepository.findTopBySeriesIdOrderByChapterNumberDesc(seriesId);
        if (entity == null) {
            throw new IllegalArgumentException("No chapter found for seriesId: " + seriesId);
        }
        return ChapterPersistenceMapper.toDomainEntity(entity);
    }

    @Override
    public void delete(Chapter chapter) {
        chapterJpaRepository.deleteById(chapter.getId());
    }

}