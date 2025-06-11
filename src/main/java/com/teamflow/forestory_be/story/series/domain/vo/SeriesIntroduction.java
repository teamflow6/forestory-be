package com.teamflow.forestory_be.story.series.domain.vo;

import com.teamflow.forestory_be.story.series.domain.exception.InvalidIntroductionException;
import java.util.Objects;

public record SeriesIntroduction(String value) {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 500;

    private static final String NULL_INTRODUCTION = "작품 소개는 null일 수 없습니다.";
    private static final String EMPTY_INTRODUCTION = "작품 소개는 공백일 수 없습니다.";
    private static final String INVALID_INTRODUCTION_LENGTH = "작품 소개는 %d자 이상 %d자 이하여야 합니다.";

    public SeriesIntroduction {
        Objects.requireNonNull(value, NULL_INTRODUCTION);
        if (value.isEmpty()) {
            throw new InvalidIntroductionException(EMPTY_INTRODUCTION);
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidIntroductionException(String.format(INVALID_INTRODUCTION_LENGTH, MIN_LENGTH, MAX_LENGTH));
        }
    }
}
