package com.teamflow.forestory_be.comment.presentation.dto.response;

import com.teamflow.forestory_be.comment.domain.entity.Comment;
import com.teamflow.forestory_be.user.domain.entity.User;
import java.time.LocalDate;

public record CommentResponse(
    Long commentId,
    String content,
    Integer replyCount,
    CommentUserResponse user,
    LocalDate createdAt
) {

    public static CommentResponse of(Comment comment, User user) {
        return new CommentResponse(
            comment.getId(),
            comment.getCommentContent().value(),
            comment.getReplyCount(),
            new CommentUserResponse(
                comment.getUserId(),
                user.getName().value(),
                user.getProfileImageUrl().value()
            ),
            comment.getCreatedAt()
        );
    }

    record CommentUserResponse(
        Long userId,
        String name,
        String profileUrl
    ) {

    }
}
