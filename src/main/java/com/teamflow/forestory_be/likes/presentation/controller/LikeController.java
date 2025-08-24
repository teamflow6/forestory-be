package com.teamflow.forestory_be.likes.presentation.controller;

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
}
