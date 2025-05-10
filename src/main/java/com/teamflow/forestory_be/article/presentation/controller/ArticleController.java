package com.teamflow.forestory_be.article.presentation.controller;

import com.teamflow.forestory_be.article.application.dto.CreateArticleCommand;
import com.teamflow.forestory_be.article.application.service.ArticleService;
import com.teamflow.forestory_be.article.presentation.request.CreateArticleRequest;
import com.teamflow.forestory_be.article.presentation.response.CreateArticleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<CreateArticleResponse> create(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid CreateArticleRequest request
    ) {
        CreateArticleCommand command = new CreateArticleCommand(
                userId,
                request.title(),
                request.subtitle(),
                request.content(),
                request.thumbnailUrl(),
                request.isDraft()
        );
        Long articleId = articleService.create(command);
        return ResponseEntity.ok(CreateArticleResponse.createFromId(articleId));
    }

    @PostMapping("/draft")
    public ResponseEntity<CreateArticleResponse> draft(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid CreateArticleRequest request
    ) {
        CreateArticleCommand command = new CreateArticleCommand(
                userId,
                request.title(),
                request.subtitle(),
                request.content(),
                request.thumbnailUrl(),
                request.isDraft()
        );
        Long articleId = articleService.create(command);
        return ResponseEntity.ok(CreateArticleResponse.draftFromId(articleId));
    }
}
