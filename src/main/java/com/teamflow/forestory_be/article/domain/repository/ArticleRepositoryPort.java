package com.teamflow.forestory_be.article.domain.repository;

import com.teamflow.forestory_be.article.domain.entity.Article;
import java.util.Optional;

public interface ArticleRepositoryPort {

    void save(Article article);

    Article getById(Long id);
}
