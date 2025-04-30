package com.teamflow.forestory_be.article.domain.repository;

import com.teamflow.forestory_be.article.domain.entity.Article;

public interface ArticleRepositoryPort {

    void save(Article article);

//    Article findById(Long id);
//    List<Article> findByAuthorId(Long authorId);
}
