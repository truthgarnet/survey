package org.kong.exception;

import java.util.Objects;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(Objects.requireNonNull(errorCode, "ErrorCode must not be null").getMsg());
        this.errorCode = errorCode;
    }

    // 외부 시스템 예외
    public CustomException(ErrorCode errorCode, Throwable cause) {
        super(Objects.requireNonNull(errorCode, "ErrorCode must not be null").getMsg(), cause);
        this.errorCode = errorCode;
    }

}
