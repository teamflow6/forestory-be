package com.teamflow.forestory_be.user.domain.vo;

public record Introduction(String value) {
    public Introduction {
        if (value == null) {
            value = "";
        }
    }
}

