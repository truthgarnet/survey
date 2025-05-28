package org.kong.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseError {

    private final HttpStatus status;
    private final String error;
    private final int code;
    private final String msg;

    ResponseError(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.error = errorCode.getError();
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }
}
