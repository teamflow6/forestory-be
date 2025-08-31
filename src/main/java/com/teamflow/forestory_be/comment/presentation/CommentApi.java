package com.teamflow.forestory_be.comment.presentation;

import com.teamflow.forestory_be.comment.presentation.dto.request.CreateCommentRequest;
import com.teamflow.forestory_be.comment.presentation.dto.request.DeleteCommentRequest;
import com.teamflow.forestory_be.comment.presentation.dto.request.UpdateCommentRequest;
import com.teamflow.forestory_be.comment.presentation.dto.response.CommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "댓글 API", description = "댓글 및 대댓글 관련 API")
@RequestMapping("/api/v1/comments")
public interface CommentApi {

    @Operation(summary = "댓글 작성", security = @SecurityRequirement(name = "AccessToken"))
    @PostMapping
    ResponseEntity<CommentResponse> createRootComment(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @RequestBody CreateCommentRequest request
    );

    @Operation(summary = "대댓글 작성", security = @SecurityRequirement(name = "AccessToken"))
    @PostMapping("/{commentId}")
    ResponseEntity<CommentResponse> createReplyComment(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @PathVariable Long commentId,
        @RequestBody CreateCommentRequest request
    );

    @Operation(summary = "댓글/대댓글 수정", security = @SecurityRequirement(name = "AccessToken"))
    @PatchMapping("/{commentId}")
    ResponseEntity<CommentResponse> updateComment(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @PathVariable Long commentId,
        @RequestBody UpdateCommentRequest request
    );

    @Operation(summary = "댓글/대댓글 삭제", security = @SecurityRequirement(name = "AccessToken"))
    @DeleteMapping("/{commentId}")
    ResponseEntity<CommentResponse> deleteComment(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @PathVariable Long commentId,
        @RequestBody DeleteCommentRequest request
    );

    @Operation(summary = "게시글 댓글 목록 조회")
    @GetMapping("/post/{postId}")
    ResponseEntity<List<CommentResponse>> getAllCommentsFromPost(
        @PathVariable Long postId
    );

    @Operation(summary = "특정 댓글의 대댓글 목록 조회")
    @GetMapping("/{commentId}")
    ResponseEntity<List<CommentResponse>> getAllReplyComment(
        @PathVariable Long commentId
    );

    @Operation(summary = "내가 작성한 댓글 목록 조회", security = @SecurityRequirement(name = "AccessToken"))
    @GetMapping
    ResponseEntity<List<CommentResponse>> getAllCommentsFromUser(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    );
}
