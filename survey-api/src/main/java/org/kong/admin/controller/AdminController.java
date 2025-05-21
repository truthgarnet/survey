package org.kong.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.kong.admin.dto.AdminLoginRequest;
import org.kong.admin.dto.AdminLoginResponse;
import org.kong.admin.facade.AdminFacade;
import org.kong.response.ResponseCommon;
import org.kong.survey.dto.PageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminFacade adminFacade;

  @PostMapping("/login")
  public ResponseEntity<ResponseCommon<Object>> login(@RequestBody AdminLoginRequest loginRequest) {
    AdminLoginResponse result = adminFacade.login(loginRequest);

    ResponseCommon<Object> response =
        ResponseCommon.builder().code(1).msg("로그인 성공").data(result).build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/logout")
  public ResponseEntity<ResponseCommon<Object>> logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    if (session != null) {
      session.invalidate();
    }

    ResponseCommon<Object> response = ResponseCommon.builder().code(1).msg("로그아웃 성공").build();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/users")
  public ResponseEntity<ResponseCommon<Object>> getUsers() {
    PageDto result = adminFacade.getUsers();

    ResponseCommon<Object> response =
        ResponseCommon.builder().code(1).msg("사용자 리스트 보기 성공").data(result).build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
