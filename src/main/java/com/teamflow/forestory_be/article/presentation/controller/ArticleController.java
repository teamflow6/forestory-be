package com.teamflow.forestory_be.article.presentation.controller;

import com.teamflow.forestory_be.article.application.dto.command.CreateArticleCommand;
import com.teamflow.forestory_be.article.application.dto.command.DeleteArticleCommand;
import com.teamflow.forestory_be.article.application.dto.command.UpdateArticleCommand;
import com.teamflow.forestory_be.article.application.dto.command.UpdateArticleStatusCommand;
import com.teamflow.forestory_be.article.application.dto.query.GetArticleQuery;
import com.teamflow.forestory_be.article.application.service.ArticleService;
import com.teamflow.forestory_be.article.domain.vo.ArticleStatus;
import com.teamflow.forestory_be.article.presentation.dto.request.CreateArticleRequest;
import com.teamflow.forestory_be.article.presentation.dto.request.UpdateArticleRequest;
import com.teamflow.forestory_be.article.presentation.dto.request.UpdateArticleStatusRequest;
import com.teamflow.forestory_be.article.presentation.dto.response.CreateArticleResponse;
import com.teamflow.forestory_be.article.presentation.dto.response.DeleteArticleResponse;
import com.teamflow.forestory_be.article.presentation.dto.response.GetArticleResponse;
import com.teamflow.forestory_be.article.presentation.dto.response.UpdateArticleResponse;
import com.teamflow.forestory_be.article.presentation.dto.response.UpdateArticleStatusResponse;
import com.teamflow.forestory_be.auth.infrastructure.security.oauth.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/articles")
@RequiredArgsConstructor
@Validated
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    @Operation(summary = "아티클 등록", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<CreateArticleResponse> create(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody @Valid CreateArticleRequest request
    ) {
        System.out.println(userId);
        CreateArticleCommand command = new CreateArticleCommand(
                userId,
                request.title(),
                request.subtitle(),
                request.content(),
                request.thumbnailUrl(),
                ArticleStatus.from(request.status())
        );
        Long articleId = articleService.create(command);
        return ResponseEntity.ok(CreateArticleResponse.createFromId(articleId));
    }

    @GetMapping("/{articleId}")
    @Operation(summary = "아티클 조회", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<GetArticleResponse> getArticle(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @PathVariable @NotNull Long articleId
    ) {
        GetArticleQuery getArticleQuery = new GetArticleQuery(articleId);
        GetArticleResponse getArticleResponse = articleService.get(getArticleQuery);
        return ResponseEntity.ok(getArticleResponse);
    }

    @PutMapping("/{articleId}")
    @Operation(summary = "아티클 수정", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<UpdateArticleResponse> updateArticle(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody @Valid UpdateArticleRequest request,
            @PathVariable @NotNull Long articleId
    ) {
        UpdateArticleCommand updateArticleCommand = new UpdateArticleCommand(
                articleId,
                userId,
                request.title(),
                request.subtitle(),
                request.content(),
                request.thumbnailUrl(),
                ArticleStatus.from(request.status())
        );
        UpdateArticleResponse updateArticleResponse = articleService.update(updateArticleCommand);
        return ResponseEntity.ok(updateArticleResponse);

    }

    @PatchMapping("{articleId}")
    @Operation(summary = "아티클 상태 수정", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<UpdateArticleStatusResponse> updateArticleStatus(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody @Valid UpdateArticleStatusRequest request,
            @PathVariable @NotNull Long articleId
    ) {
        UpdateArticleStatusCommand updateArticleStatusCommand = new UpdateArticleStatusCommand(
                articleId,
                userId,
                ArticleStatus.from(request.status())
        );
        UpdateArticleStatusResponse updateArticleStatusResponse = articleService.updateStatus(
                updateArticleStatusCommand);
        return ResponseEntity.ok(updateArticleStatusResponse);
    }


    @DeleteMapping("/{articleId}")
    @Operation(summary = "아티클 삭제", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<DeleteArticleResponse> deleteArticle(
            @AuthenticationPrincipal Long userId,
            @PathVariable @NotNull Long articleId
    ) {
        DeleteArticleCommand deleteArticleCommand = new DeleteArticleCommand(
                userId,
                articleId
        );
        DeleteArticleResponse deleteArticleResponse = articleService.delete(deleteArticleCommand);
        return ResponseEntity.ok(deleteArticleResponse);
    }
}