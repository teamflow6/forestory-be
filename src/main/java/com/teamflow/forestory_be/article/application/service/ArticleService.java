package com.teamflow.forestory_be.article.application.service;

import com.teamflow.forestory_be.article.application.dto.CreateArticleCommand;
import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.article.domain.repository.ArticleRepositoryPort;
import com.teamflow.forestory_be.article.domain.vo.Content;
import com.teamflow.forestory_be.article.domain.vo.Subtitle;
import com.teamflow.forestory_be.article.domain.vo.Title;
import com.teamflow.forestory_be.image.application.dto.UploadImageCommand;
import com.teamflow.forestory_be.image.application.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepositoryPort articleRepositoryPort;
    private final ImageService imageService;

    @Transactional
    public Long create(CreateArticleCommand command) {
        Title title = new Title(command.title());
        Subtitle subtitle = new Subtitle(command.subtitle());
        Content content = new Content(command.content());
        String thumbnailUrl = command.thumbnailUrl();

        if (command.isDraft()) {
            Article article = Article.draft(command.authorId(), title, subtitle, content, command.thumbnailUrl());
            articleRepositoryPort.save(article);
            return article.getId();
        } else {
            Article article = Article.create(command.authorId(), title, subtitle, content, command.thumbnailUrl());
            articleRepositoryPort.save(article);
            return article.getId();
        }

    }

}
