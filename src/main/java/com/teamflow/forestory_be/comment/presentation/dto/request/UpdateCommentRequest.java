package com.teamflow.forestory_be.comment.presentation.dto.request;

public record UpdateCommentRequest(
    Long postId,
    String content
) {

}
