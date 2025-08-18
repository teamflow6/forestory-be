package com.teamflow.forestory_be.support.common.advice;

import com.teamflow.forestory_be.auth.domain.exception.AlreadyExpiredTokenException;
import com.teamflow.forestory_be.auth.domain.exception.InvalidTokenException;
import com.teamflow.forestory_be.auth.domain.exception.NotSupportSocialTypeException;
import com.teamflow.forestory_be.auth.domain.exception.UnMatchUserException;
import com.teamflow.forestory_be.support.common.exception.CustomException;
import java.nio.file.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice(basePackages = "com.teamflow.forestory_be")
public class GlobalExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseErrorResponse> handleCustomException(CustomException ex) {
        log.warn(ex.getLocalizedMessage());


        BaseErrorResponse body = new BaseErrorResponse(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({
        AlreadyExpiredTokenException.class,
        InvalidTokenException.class,
        NotSupportSocialTypeException.class,
        UnMatchUserException.class
    })
    public ResponseEntity<BaseErrorResponse> handleAuthenticationException(Exception ex) {
        log.warn(ex.getLocalizedMessage());

        BaseErrorCode errorCode = BaseErrorCode.AUTHENTICATION_ERROR;
        BaseErrorResponse body = new BaseErrorResponse(errorCode.name(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.warn(ex.getLocalizedMessage());

        BaseErrorCode errorCode = BaseErrorCode.ACCESS_DENIED_ERROR;
        BaseErrorResponse body = new BaseErrorResponse(errorCode.name(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(body);
    }

    @ExceptionHandler({
        IllegalArgumentException.class,
        NoResourceFoundException.class,
        MissingRequestCookieException.class,
        MethodArgumentNotValidException.class,
        HttpMessageNotReadableException.class,
        MethodArgumentTypeMismatchException.class,
        HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<BaseErrorResponse> handleInvalidRequestException(Exception e) {
        log.warn(e.getLocalizedMessage());

        BaseErrorCode errorCode = BaseErrorCode.INVALID_REQUEST_ERROR;
        BaseErrorResponse body = new BaseErrorResponse(errorCode.name(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseErrorResponse> handleUnCatchException(Exception e) {
        log.error("Uncaught exception: {}", e.getClass(), e);

        BaseErrorCode errorCode = BaseErrorCode.UNKNOWN_ERROR;
        BaseErrorResponse body = new BaseErrorResponse(errorCode.name(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(body);
    }
}
