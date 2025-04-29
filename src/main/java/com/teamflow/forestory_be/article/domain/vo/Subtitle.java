package com.teamflow.forestory_be.article.domain.vo;

import com.teamflow.forestory_be.article.domain.exception.InvalidSubtitleException;
import java.util.Objects;

public record Subtitle(String value) {
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 50;

    private static final String NULL_SUBTITLE = "부제목은 null일 수 없습니다.";
    private static final String EMPTY_SUBTITLE = "부제목은 공백일 수 없습니다.";
    private static final String INVALID_SUBTITLE_LENGTH = "부제목은 %d자 이상 %d자 이하여야 합니다.";


    public Subtitle {
        Objects.requireNonNull(value, NULL_SUBTITLE);
        if (value.isEmpty()) {
            throw new InvalidSubtitleException(EMPTY_SUBTITLE);
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidSubtitleException(String.format(INVALID_SUBTITLE_LENGTH, MIN_LENGTH, MAX_LENGTH));
        }
    }
}


