package com.teamflow.forestory_be.story.series.domain.vo;

import com.teamflow.forestory_be.story.series.domain.exception.InvalidSeriesStatusException;

public enum SeriesStatus {
    DRAFT_OVERVIEW, PENDING_FIRST_CHAPTER, PUBLISHED;

    public static SeriesStatus from(String value) {
        try {
            return SeriesStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidSeriesStatusException(value);
        }
    }
}
