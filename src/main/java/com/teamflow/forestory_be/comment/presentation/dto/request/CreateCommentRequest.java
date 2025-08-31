package com.teamflow.forestory_be.comment.presentation.dto.request;

public record CreateCommentRequest(
    Long postId,
    String content
) {

}
