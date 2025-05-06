package com.teamflow.forestory_be.support.common.advice;

public record BaseErrorResponse(
    String code,
    String message
) {
}
