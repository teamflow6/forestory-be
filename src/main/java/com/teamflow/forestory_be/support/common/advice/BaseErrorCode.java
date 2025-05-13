package com.teamflow.forestory_be.support.common.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseErrorCode {
    INVALID_REQUEST_ERROR("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_ERROR("잘못된 인증 정보입니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED_ERROR("잘못된 접근 권한입니다.", HttpStatus.FORBIDDEN),
    UNKNOWN_ERROR("알 수 없는 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String message;
    private final HttpStatus status;

    BaseErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
