package com.teamflow.forestory_be.story.chapter.infrastructure.persistence.repository;

import static com.querydsl.core.types.Projections.constructor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;

import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.QChapterJpaEntity;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterWithCreatedAt;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChapterQueryRepositoryImpl implements ChapterQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChapterWithCreatedAt> findChaptersBySeriesIdWithScroll(Long seriesId, String sort, String lastNumber, int size) {
        QChapterJpaEntity chapter = QChapterJpaEntity.chapterJpaEntity;

        BooleanBuilder builder = new BooleanBuilder()
                .and(chapter.seriesId.eq(seriesId))
                .and(chapter.status.eq(ChapterStatus.PUBLISHED));

        if ("asc".equals(sort) && lastNumber != null) {
            builder.and(chapter.chapterNumber.gt(lastNumber));
        } else if ("desc".equals(sort) && lastNumber != null) {
            builder.and(chapter.chapterNumber.lt(lastNumber));
        }

        return queryFactory
                .select(constructor(ChapterWithCreatedAt.class,
                        chapter,
                        chapter.createdAt))
                .from(chapter)
                .where(builder)
                .orderBy("asc".equals(sort)
                        ? chapter.chapterNumber.asc()
                        : chapter.chapterNumber.desc())
                .limit(size)
                .fetch();
    }
}
