package com.teamflow.forestory_be.user.domain.vo;

public record Introduction(String value) {
    public static Introduction empty() {
        return new Introduction("");
    }

    public static Introduction fromNullable(String newValue, Introduction value) {
        if (newValue == null || newValue.isEmpty()) {
            return value;
        }
        return new Introduction(newValue);
    }
}

