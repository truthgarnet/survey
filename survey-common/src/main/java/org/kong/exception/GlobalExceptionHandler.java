package org.kong.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseError> handleCustomException(CustomException e) {
        log.error("CustomException: {}", e.getMessage());
        ResponseError response = new ResponseError(
            e.getErrorCode().getStatus(),
            e.getErrorCode().getError(),
            e.getErrorCode().getCode(),
            e.getErrorCode().getMsg()
        );
        return new ResponseEntity<>(response, e.getErrorCode().getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleException(Exception e) {
        log.error("Unexpected error occurred: ", e);
        ResponseError response = new ResponseError(
            ErrorCode.INTERNAL_SERVER_ERROR.getStatus(),
            ErrorCode.INTERNAL_SERVER_ERROR.getError(),
            ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
            ErrorCode.INTERNAL_SERVER_ERROR.getMsg()
        );
        return new ResponseEntity<>(response, ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
    }
}
