package com.teamflow.forestory_be.comment.application.dto;

public record DeleteCommentCommand(
    Long userId,
    Long postId,
    Long commentId
) {

}
