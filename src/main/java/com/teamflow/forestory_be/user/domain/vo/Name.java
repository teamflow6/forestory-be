package com.teamflow.forestory_be.user.domain.vo;

import com.teamflow.forestory_be.user.domain.exception.InvalidUserNameException;
import java.util.Objects;

public record Name(String value) {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 7;

    private static final String NULL_NAME = "이름을 null일 수 없습니다.";
    private static final String EMPTY_NAME = "이름은 공백일 수 없습니다.";
    private static final String INVALID_NAME_LENGTH = "이름은 %d자 이상 %d자 이하여야 합니다.";

    public Name {
        Objects.requireNonNull(value, NULL_NAME);
        if (value.isEmpty()) {
            throw new InvalidUserNameException(EMPTY_NAME);
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidUserNameException(String.format(INVALID_NAME_LENGTH, MIN_LENGTH, MAX_LENGTH));
        }
    }
}
