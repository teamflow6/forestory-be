package com.teamflow.forestory_be.home.application.dto.query;

public record GetWeeklyTopByTypeQuery(int limit) {
    public static GetWeeklyTopByTypeQuery of(Integer limit) {
        return new GetWeeklyTopByTypeQuery(limit == null ? 3 : Math.max(1, limit));
    }
}
