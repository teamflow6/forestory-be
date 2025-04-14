package com.teamflow.forestory_be.support.common.exception;

/**
 * 모든 도메인 예외의 최상위 추상 클래스입니다. 공통적으로 사용하는 예외 코드(code)와 메시지(message)를 포함합니다.
 * <p>
 * 각 도메인별 BaseException은 해당 클래스를 상속받아 구현합니다.
 *
 * @author junseoparkk
 */
public abstract class CustomException extends RuntimeException {

    private final String code;

    protected CustomException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
