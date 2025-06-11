package com.teamflow.forestory_be.story.chapter.domain.vo;

import com.teamflow.forestory_be.story.chapter.domain.exception.InvalidChapterStatusException;

public enum ChapterStatus {
    DRAFT, PUBLISHED;

    public static ChapterStatus from(String value) {
        try {
            return ChapterStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidChapterStatusException(value);
        }
    }
}
