package com.teamflow.forestory_be.draft.infrastructure.repository;

import com.teamflow.forestory_be.article.infrastructure.persistence.entity.ArticleJpaEntity;
import com.teamflow.forestory_be.article.infrastructure.persistence.repository.ArticleJpaRepository;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.repository.ChapterJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DraftReadAdaptor implements DraftReadPort{

    private final ArticleJpaRepository articleRepository;
    private final ChapterJpaRepository chapterRepository;

    @Override
    public List<ArticleJpaEntity> sliceArticles(Long userId, LocalDateTime cursorCreatedAt, int limitPlusOne) {
        return articleRepository.sliceArticle(userId, cursorCreatedAt, PageRequest.of(0, limitPlusOne));
    }

    @Override
    public List<ChapterJpaEntity> sliceChaptersBySeriesType(Long userId, String type, LocalDateTime cursorCreatedAt, int limitPlusOne) {
        // Chapter 조회는 Native SQL → limit 직접 전달해야 함
        return chapterRepository.sliceChaptersBySeriesType(userId, cursorCreatedAt, type, limitPlusOne);
    }
}
