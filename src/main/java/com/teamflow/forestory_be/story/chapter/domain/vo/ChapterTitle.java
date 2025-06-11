package com.teamflow.forestory_be.story.chapter.domain.vo;

import com.teamflow.forestory_be.article.domain.exception.InvalidTitleException;
import com.teamflow.forestory_be.story.chapter.domain.exception.InvalidChapterTitleException;
import java.util.Objects;

public record ChapterTitle(String value) {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 50;

    private static final String NULL_TITLE = "챕터 제목은 null일 수 없습니다.";
    private static final String EMPTY_TITLE = "챕터 제목은 공백일 수 없습니다.";
    private static final String INVALID_TITLE_LENGTH = "챕터 제목은 %d자 이상 %d자 이하여야 합니다.";


    public ChapterTitle {
        Objects.requireNonNull(value, NULL_TITLE);
        if (value.isEmpty()) {
            throw new InvalidChapterTitleException(EMPTY_TITLE);
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidChapterTitleException(String.format(INVALID_TITLE_LENGTH, MIN_LENGTH, MAX_LENGTH));
        }
    }
}
