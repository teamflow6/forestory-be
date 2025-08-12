package com.teamflow.forestory_be.story.chapter.infrastructure.persistence.repository;

import static com.querydsl.core.types.Projections.constructor;
import static com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.QChapterJpaEntity.chapterJpaEntity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.NeighborChapter;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterWithCreatedAt;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChapterQueryRepositoryImpl implements ChapterQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 시리즈별 발행 챕터 스크롤 조회 (키셋 페이징)
     * - sort=asc  : chapterNumber 오름차순, lastChapterNumber보다 큰 것
     * - sort=desc : chapterNumber 내림차순, lastChapterNumber보다 작은 것
     * - lastChapterNumber <= 0 이면 커서 조건 생략(첫 페이지)
     */
    @Override
    public List<ChapterWithCreatedAt> findChaptersBySeriesIdWithScroll(Long seriesId,
                                                                       String sort,
                                                                       int lastChapterNumber,
                                                                       int size) {
        BooleanBuilder where = new BooleanBuilder()
                .and(chapterJpaEntity.seriesId.eq(seriesId))
                .and(chapterJpaEntity.status.eq(ChapterStatus.PUBLISHED));

        boolean asc = "asc".equalsIgnoreCase(sort);
        if (lastChapterNumber > 0) {
            if (asc) {
                where.and(chapterJpaEntity.chapterNumber.gt(lastChapterNumber));
            } else {
                where.and(chapterJpaEntity.chapterNumber.lt(lastChapterNumber));
            }
        }

        return queryFactory
                .select(constructor(
                        ChapterWithCreatedAt.class,
                        chapterJpaEntity,
                        chapterJpaEntity.createdAt
                ))
                .from(chapterJpaEntity)
                .where(where)
                .orderBy(
                        asc ? chapterJpaEntity.chapterNumber.asc() : chapterJpaEntity.chapterNumber.desc(),
                        asc ? chapterJpaEntity.id.asc()            : chapterJpaEntity.id.desc() // tie-breaker
                )
                .limit(size)
                .fetch();
    }

    /**
     * 현재 회차 포함 + 다음 N개 (총 1 + nextCount)
     */
    @Override
    public List<NeighborChapter> findAroundPublishedChapters(Long seriesId,
                                                             int currentChapterNumber,
                                                             int nextCount) {
        int total = 1 + nextCount; // 현재 포함

        return queryFactory
                .select(constructor(
                        NeighborChapter.class,
                        chapterJpaEntity.id,
                        chapterJpaEntity.chapterNumber,
                        chapterJpaEntity.title
                ))
                .from(chapterJpaEntity)
                .where(
                        chapterJpaEntity.seriesId.eq(seriesId),
                        chapterJpaEntity.status.eq(ChapterStatus.PUBLISHED),
                        chapterJpaEntity.chapterNumber.goe(currentChapterNumber) // 현재 포함
                )
                .orderBy(chapterJpaEntity.chapterNumber.asc(), chapterJpaEntity.id.asc())
                .limit(total)
                .fetch();
    }
}
