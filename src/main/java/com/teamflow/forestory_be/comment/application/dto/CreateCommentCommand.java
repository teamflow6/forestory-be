package com.teamflow.forestory_be.comment.application.dto;

public record CreateCommentCommand(
    Long userId,
    Long postId,
    Long parentCommentId,
    String content
) {

    public static CreateCommentCommand toRootCommand(Long userId, Long postId, String content) {
        return new CreateCommentCommand(userId, postId, null, content);
    }

    public static CreateCommentCommand toReplyCommand(Long userId, Long postId, Long parentCommentId, String content) {
        return new CreateCommentCommand(userId, postId, parentCommentId, content);
    }
}
