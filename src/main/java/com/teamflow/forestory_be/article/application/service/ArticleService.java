package com.teamflow.forestory_be.article.application.service;

import com.teamflow.forestory_be.article.application.dto.CreateArticleCommand;
import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.article.domain.repository.ArticleRepositoryPort;
import com.teamflow.forestory_be.article.domain.vo.Content;
import com.teamflow.forestory_be.article.domain.vo.Subtitle;
import com.teamflow.forestory_be.article.domain.vo.Title;
import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.repository.UserRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final UserRepositoryPort userRepositoryPort;
    private final ArticleRepositoryPort articleRepositoryPort;

    private Article toArticle(CreateArticleCommand command, boolean isDraft) {
        User author = userRepositoryPort.getById(command.authorId());
        Title title = new Title(command.title());
        Subtitle subtitle = new Subtitle(command.subtitle());
        Content content = new Content(command.content());

        return isDraft
            ? Article.draft(author, title, subtitle, content, command.thumbnailUrl())
            : Article.create(author, title, subtitle, content, command.thumbnailUrl());
    }


    @Transactional
    public Long create(CreateArticleCommand command) {
        Article article = toArticle(command, false);
        articleRepositoryPort.save(article);
        return article.getId();
    }

    @Transactional
    public Long draft(CreateArticleCommand command) {
        Article article = toArticle(command, true);
        articleRepositoryPort.save(article);
        return article.getId();
    }

}
