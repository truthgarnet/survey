package org.kong.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "U001", 101, "사용자를 찾을 수 없습니다."),
  LOGIN_FAILD(HttpStatus.BAD_REQUEST, "U002", 102, "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요."),
  ADMIN_NOT_FOUND(HttpStatus.BAD_REQUEST, "A001", 101, "관리자를 찾을 수 없습니다."),
  SERVICE_NOT_FOUND(HttpStatus.BAD_REQUEST, "S001", 101, "설문지를 찾을 수 없습니다."),
  QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "Q001", 101, "질문지를 찾을 수 없습니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", 500, "서버 내부 오류가 발생했습니다.");

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
