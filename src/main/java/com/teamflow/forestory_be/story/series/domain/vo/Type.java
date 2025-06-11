package com.teamflow.forestory_be.story.series.domain.vo;

import com.teamflow.forestory_be.story.series.domain.exception.InvalidTypeException;

public enum Type {
    NOVEL, ESSAY;

    public static Type from(String value) {
        try {
            return Type.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidTypeException(value);
        }
    }
}
