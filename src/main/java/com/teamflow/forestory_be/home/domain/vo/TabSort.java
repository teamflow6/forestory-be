package com.teamflow.forestory_be.home.domain.vo;

public enum TabSort {
    POPULAR, LATEST;

    public static TabSort from(String s) {
        if (s == null) return POPULAR;
        return "LATEST".equalsIgnoreCase(s) ? LATEST : POPULAR;
    }
}
