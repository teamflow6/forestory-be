package com.teamflow.forestory_be.article.infrastructure.persistence;

import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.article.domain.repository.ArticleRepositoryPort;
import com.teamflow.forestory_be.article.infrastructure.persistence.entity.ArticleJpaEntity;
import com.teamflow.forestory_be.article.infrastructure.persistence.repository.ArticleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticlePersistenceAdaptor implements ArticleRepositoryPort{

    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public void save(Article article) {
        ArticleJpaEntity articleJpaEntity = ArticlePersistenceMapper.toJpaEntity(article);
        articleJpaRepository.save(articleJpaEntity);
    }


}
