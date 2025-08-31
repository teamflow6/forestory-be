package com.teamflow.forestory_be.comment.application.dto;

public record UpdateCommentCommand(
    Long userId,
    Long postId,
    Long commentId,
    String content
) {

}
