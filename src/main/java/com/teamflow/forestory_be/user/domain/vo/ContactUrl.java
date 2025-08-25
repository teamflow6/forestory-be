package com.teamflow.forestory_be.user.domain.vo;

public record ContactUrl(
    String value
) {
    public static ContactUrl empty() {
        return new ContactUrl("");
    }

    public static ContactUrl fromNullable(String newValue, ContactUrl value) {
        if (newValue == null || newValue.isEmpty()) {
            return value;
        }
        return new ContactUrl(newValue);
    }
}
