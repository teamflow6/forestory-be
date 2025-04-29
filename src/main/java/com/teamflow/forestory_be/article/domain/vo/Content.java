package com.teamflow.forestory_be.article.domain.vo;

import com.teamflow.forestory_be.article.domain.exception.InvalidContentException;
import java.util.Objects;

public record Content(String value) {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 3000;

    private static final String NULL_CONTENT = "내용은 null일 수 없습니다.";
    private static final String EMPTY_CONTENT = "내용은 공백일 수 없습니다.";
    private static final String INVALID_CONTENT_LENGTH = "내용은 %d자 이상 %d자 이하여야 합니다.";

    public Content {
        Objects.requireNonNull(value, NULL_CONTENT);
        if (value.isEmpty()) {
            throw new InvalidContentException(EMPTY_CONTENT);
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidContentException(String.format(INVALID_CONTENT_LENGTH, MIN_LENGTH, MAX_LENGTH));
        }
    }
}

