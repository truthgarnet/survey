package org.kong.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.OK, "U001", 101, "사용자를 찾을 수 없습니다."),
    SERVICE_NOT_FOUND(HttpStatus.OK, "S001", 101, "설문지를 찾을 수 없습니다."),
    QUESTION_NOT_FOUND(HttpStatus.OK, "Q001", 101, "질문지를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String error;
    private final int code;
    private final String msg;

    ErrorCode(HttpStatus status, String error, int code, String msg) {
        this.status = status;
        this.error = error;
        this.code = code;
        this.msg = msg;
    }
}
