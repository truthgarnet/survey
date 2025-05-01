package org.kong.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseError {

    private final HttpStatus status;
    private final String error;
    private final int code;
    private final String msg;

    ResponseError(HttpStatus status, String error, int code, String msg) {
        this.status = status;
        this.error = error;
        this.code = code;
        this.msg = msg;
    }
}
