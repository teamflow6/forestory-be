package com.teamflow.forestory_be.likes.presentation.controller;

import com.teamflow.forestory_be.likes.application.dto.command.BulkDeleteLikeCommand;
import com.teamflow.forestory_be.likes.application.dto.query.GetMyLikesQuery;
import com.teamflow.forestory_be.likes.presentation.dto.request.BulkDeleteLikeRequest;
import com.teamflow.forestory_be.likes.presentation.dto.response.GetMyLikesResponse;
import  com.teamflow.forestory_be.likes.presentation.dto.response.LikeResponse;
import com.teamflow.forestory_be.likes.application.service.LikeService;
import com.teamflow.forestory_be.likes.application.dto.command.CreateLikeCommand;
import com.teamflow.forestory_be.likes.application.dto.command.DeleteLikeCommand;
import com.teamflow.forestory_be.likes.presentation.dto.request.CreateLikeRequest;
import com.teamflow.forestory_be.likes.presentation.dto.request.DeleteLikeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
@Validated
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    @Operation(summary = "좋아요 생성", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<LikeResponse> createLike(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody @Valid CreateLikeRequest request
    ) {
        CreateLikeCommand command = new CreateLikeCommand(
                userId,
                request.targetType(),
                request.targetId()
        );
        LikeResponse response = likeService.create(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Operation(summary = "좋아요 취소", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<LikeResponse> deleteLike(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody @Valid DeleteLikeRequest request
    ) {
        DeleteLikeCommand command = new DeleteLikeCommand(
                userId,
                request.targetType(),
                request.targetId()
        );
        LikeResponse response = likeService.delete(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "내가 좋아요한 목록(무한 스크롤)",
            description = "likes.updated_at DESC, like_id DESC 키셋 페이징",
            security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<GetMyLikesResponse> getMyLikes(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) LocalDateTime cursorUpdatedAt,
            @RequestParam(required = false) Long cursorLikeId
    ) {
        GetMyLikesQuery q = new GetMyLikesQuery(userId, size, cursorUpdatedAt, cursorLikeId);
        return ResponseEntity.ok(likeService.getMyLikes(q));
    }

    @DeleteMapping("/bulk")
    @Operation(summary = "좋아요 일괄 삭제(타입 혼합 지원)", security = @SecurityRequirement(name = "AccessToken"))
    public ResponseEntity<Void> bulkDeleteLikes(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody @Valid BulkDeleteLikeRequest request
    ) {
        likeService.bulkDelete(new BulkDeleteLikeCommand(userId, request.targets()));
        return ResponseEntity.noContent().build();
    }

}
