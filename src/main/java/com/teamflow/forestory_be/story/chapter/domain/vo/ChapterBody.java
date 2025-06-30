package com.teamflow.forestory_be.story.chapter.domain.vo;

import com.teamflow.forestory_be.article.domain.exception.InvalidContentException;
import com.teamflow.forestory_be.story.chapter.domain.exception.InvalidChapterBodyException;
import java.util.Objects;

public record ChapterBody(String value) {
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 3000;

    private static final String NULL_CONTENT = "내용은 null일 수 없습니다.";
    private static final String EMPTY_CONTENT = "내용은 공백일 수 없습니다.";
    private static final String INVALID_CONTENT_LENGTH = "내용은 %d자 이상 %d자 이하여야 합니다.";

    public ChapterBody {
        Objects.requireNonNull(value, NULL_CONTENT);
        if (value.isEmpty()) {
            throw new InvalidChapterBodyException(EMPTY_CONTENT);
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidChapterBodyException(String.format(INVALID_CONTENT_LENGTH, MIN_LENGTH, MAX_LENGTH));
        }
    }
}
