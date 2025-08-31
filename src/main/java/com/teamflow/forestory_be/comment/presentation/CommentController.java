package com.teamflow.forestory_be.comment.presentation;


import com.teamflow.forestory_be.comment.application.dto.CreateCommentCommand;
import com.teamflow.forestory_be.comment.application.dto.DeleteCommentCommand;
import com.teamflow.forestory_be.comment.application.dto.UpdateCommentCommand;
import com.teamflow.forestory_be.comment.application.service.CommentService;
import com.teamflow.forestory_be.comment.presentation.dto.request.CreateCommentRequest;
import com.teamflow.forestory_be.comment.presentation.dto.request.DeleteCommentRequest;
import com.teamflow.forestory_be.comment.presentation.dto.request.UpdateCommentRequest;
import com.teamflow.forestory_be.comment.presentation.dto.response.CommentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createRootComment(
        @AuthenticationPrincipal Long userId,
        @RequestBody CreateCommentRequest request
    ) {
        CreateCommentCommand command = CreateCommentCommand.toRootCommand(
            userId,
            request.postId(),
            request.content()
        );
        commentService.create(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commentId}")
    public ResponseEntity<CommentResponse> createReplyComment(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long commentId,
        @RequestBody CreateCommentRequest request
    ) {
        CreateCommentCommand command = CreateCommentCommand.toReplyCommand(
            userId,
            request.postId(),
            commentId,
            request.content()
        );
        commentService.create(command);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long commentId,
        @RequestBody UpdateCommentRequest request
    ) {
        UpdateCommentCommand command = new UpdateCommentCommand(
            userId,
            request.postId(),
            commentId,
            request.content()
        );
        CommentResponse response = commentService.update(command);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(
        @AuthenticationPrincipal Long userId,
        @PathVariable Long commentId,
        @RequestBody DeleteCommentRequest request
    ) {
        DeleteCommentCommand command = new DeleteCommentCommand(
            userId,
            request.postId(),
            commentId
        );
        commentService.delete(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsFromPost(
        @PathVariable Long postId
    ) {
        List<CommentResponse> response = commentService.getAllRootCommentByPostId(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<List<CommentResponse>> getAllReplyComment(
        @PathVariable Long commentId
    ) {
        List<CommentResponse> response = commentService.getAllReplyCommentByComment(commentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllCommentsFromUser(
        @AuthenticationPrincipal Long userId
    ) {
        List<CommentResponse> response = commentService.getAllCommentByUserId(userId);
        return ResponseEntity.ok(response);
    }
}
