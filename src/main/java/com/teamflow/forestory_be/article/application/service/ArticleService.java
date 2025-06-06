package com.teamflow.forestory_be.article.application.service;

import com.teamflow.forestory_be.article.application.dto.command.CreateArticleCommand;
import com.teamflow.forestory_be.article.application.dto.command.DeleteArticleCommand;
import com.teamflow.forestory_be.article.application.dto.command.UpdateArticleCommand;
import com.teamflow.forestory_be.article.application.dto.query.GetArticleQuery;
import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.article.domain.repository.ArticleRepositoryPort;
import com.teamflow.forestory_be.article.domain.vo.ArticleStatus;
import com.teamflow.forestory_be.article.domain.vo.Content;
import com.teamflow.forestory_be.article.domain.vo.Subtitle;
import com.teamflow.forestory_be.article.domain.vo.Title;
import com.teamflow.forestory_be.article.presentation.dto.response.DeleteArticleResponse;
import com.teamflow.forestory_be.article.presentation.dto.response.GetArticleResponse;
import com.teamflow.forestory_be.article.presentation.dto.response.UpdateArticleResponse;
import com.teamflow.forestory_be.user.domain.entity.User;
import com.teamflow.forestory_be.user.domain.repository.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepositoryPort articleRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Transactional
    public Long create(CreateArticleCommand command) {
        Title title = new Title(command.title());
        Subtitle subtitle = new Subtitle(command.subtitle());
        Content content = new Content(command.content());

        if (command.status() == ArticleStatus.DRAFT) {
            Article article = Article.draft(command.authorId(), title, subtitle, content, command.thumbnailUrl());
            articleRepositoryPort.save(article);
            return article.getId();
        } else {
            Article article = Article.create(command.authorId(), title, subtitle, content, command.thumbnailUrl());
            articleRepositoryPort.save(article);
            return article.getId();
        }

    }

    @Transactional(readOnly = true)
    public GetArticleResponse get(GetArticleQuery query) {
        Article article = articleRepositoryPort.getById(query.articleId());
        User author = userRepositoryPort.getById(article.getAuthorId());

        GetArticleResponse getArticleResponse = GetArticleResponse.of(article, author);
        return getArticleResponse;
    }

    @Transactional
    public UpdateArticleResponse update(UpdateArticleCommand command) {
        Article existingArticle = articleRepositoryPort.getById(command.articleId());
        existingArticle.validateOwnerOrThrow(command.authorId());

        Article updatedArticle = existingArticle.update(
                new Title(command.title()),
                new Subtitle(command.subtitle()),
                new Content(command.content()),
                command.thumbnailUrl(),
                command.status()
        );

        articleRepositoryPort.save(updatedArticle);

        return UpdateArticleResponse.from(command.articleId());
    }

    @Transactional
    public DeleteArticleResponse delete(DeleteArticleCommand command) {
        Article article = articleRepositoryPort.getById(command.articleId());

        article.validateOwnerOrThrow(command.authorId());
        articleRepositoryPort.deleteById(command.articleId());
        return DeleteArticleResponse.from(command.articleId());

    }


}
