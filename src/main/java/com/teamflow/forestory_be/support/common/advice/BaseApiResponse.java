package com.teamflow.forestory_be.support.common.advice;

import java.time.Instant;

public record BaseApiResponse(
    String path,
    Object results,
    Long timestamp
) {
    public BaseApiResponse(String path, Object results) {
        this(path, results, Instant.now().getEpochSecond());
    }

    public BaseApiResponse(String path) {
        this(path, null, Instant.now().getEpochSecond());
    }
}
