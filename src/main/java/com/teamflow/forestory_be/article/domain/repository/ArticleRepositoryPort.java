package com.teamflow.forestory_be.article.domain.repository;

import com.teamflow.forestory_be.article.domain.entity.Article;
import java.util.List;

public interface ArticleRepositoryPort {

    void save(Article article);

    Article getById(Long id);

    void deleteById(Long id);

    List<Article> findRandomPublishedByAuthor(Long authorId, Long excludeArticleId, int limit);

    void increaseLikeCount(Long articleId);

    void decreaseLikeCount(Long articleId);
}
