package com.teamflow.forestory_be.article.infrastructure.persistence;

import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.article.domain.vo.Content;
import com.teamflow.forestory_be.article.domain.vo.Subtitle;
import com.teamflow.forestory_be.article.domain.vo.Title;
import com.teamflow.forestory_be.article.infrastructure.persistence.entity.ArticleJpaEntity;


public class ArticlePersistenceMapper {

    private ArticlePersistenceMapper() {
    }

    public static Article toDomainEntity(ArticleJpaEntity articleJpaEntity) {
        return Article.reconstruct(
                articleJpaEntity.getId(),
                articleJpaEntity.getAuthorId(),
                new Title(articleJpaEntity.getTitle()),
                new Subtitle(articleJpaEntity.getSubtitle()),
                new Content(articleJpaEntity.getContent()),
                articleJpaEntity.getThumbnailUrl(),
                articleJpaEntity.getStatus()
        );
    }

    public static ArticleJpaEntity toJpaEntity(Article article) {
        return ArticleJpaEntity.builder()
                .id(article.getId())
                .authorId(article.getAuthorId())
                .title(article.getTitle().value())
                .subtitle(article.getSubtitle().value())
                .content(article.getContent().value())
                .thumbnailUrl(article.getThumbnailUrl())
                .status(article.getStatus())
                .build();
    }
}

