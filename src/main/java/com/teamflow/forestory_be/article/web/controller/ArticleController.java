package com.teamflow.forestory_be.article.web.controller;

import com.teamflow.forestory_be.article.application.dto.CreateArticleCommand;
import com.teamflow.forestory_be.article.application.service.ArticleService;
import com.teamflow.forestory_be.article.web.request.CreateArticleRequest;
import com.teamflow.forestory_be.article.web.response.CreateArticleResponse;
import com.teamflow.forestory_be.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<CreateArticleResponse> create(@RequestBody CreateArticleRequest request) {
        CreateArticleCommand command = new CreateArticleCommand(
            request.authorId(),
            request.title(),
            request.subtitle(),
            request.content(),
            request.thumbnailUrl()
        );
       Long articleId =  articleService.create(command);
       CreateArticleResponse response = CreateArticleResponse.of(articleId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/draft")
    public ResponseEntity<CreateArticleResponse> draft(@RequestBody CreateArticleRequest request) {
        CreateArticleCommand command = new CreateArticleCommand(
            request.authorId(),
            request.title(),
            request.subtitle(),
            request.content(),
            request.thumbnailUrl()
        );
        Long articleId = articleService.draft(command);
        CreateArticleResponse response = CreateArticleResponse.of(articleId);
        return ResponseEntity.ok(response);

    }
}
