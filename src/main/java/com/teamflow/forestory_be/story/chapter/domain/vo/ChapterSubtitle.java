package com.teamflow.forestory_be.story.chapter.domain.vo;

import com.teamflow.forestory_be.article.domain.exception.InvalidSubtitleException;
import com.teamflow.forestory_be.story.chapter.domain.exception.InvalidChapterSubtitleException;
import java.util.Objects;

public record ChapterSubtitle(String value) {
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 50;

    private static final String NULL_SUBTITLE = "부제목은 null일 수 없습니다.";
    private static final String EMPTY_SUBTITLE = "부제목은 공백일 수 없습니다.";
    private static final String INVALID_SUBTITLE_LENGTH = "부제목은 %d자 이상 %d자 이하여야 합니다.";


    public ChapterSubtitle {
        Objects.requireNonNull(value, NULL_SUBTITLE);
        if (value.isEmpty()) {
            throw new InvalidChapterSubtitleException(EMPTY_SUBTITLE);
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidChapterSubtitleException(String.format(INVALID_SUBTITLE_LENGTH, MIN_LENGTH, MAX_LENGTH));
        }
    }
}
