package com.teamflow.forestory_be.comment.domain.vo;

import com.teamflow.forestory_be.comment.domain.exception.InvalidCommentContentException;
import java.util.Objects;

public record CommentContent(
    String value
) {

    private static final int MIN_CONTENT_LENGTH = 1;
    private static final int MAX_CONTENT_LENGTH = 100;

    public CommentContent {
        Objects.requireNonNull(value, "댓글 내용이 없습니다.");
        if (value.isBlank()) {
            throw new InvalidCommentContentException("댓글은 최소 " + MIN_CONTENT_LENGTH + "자 이상이어야 합니다.");
        }
        if (value.length() > MAX_CONTENT_LENGTH) {
            throw new InvalidCommentContentException("댓글은 " + MAX_CONTENT_LENGTH + "자 이하만 가능합니다.");
        }
    }
}
