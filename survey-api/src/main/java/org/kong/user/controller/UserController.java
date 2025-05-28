package org.kong.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.kong.auth.facade.AuthFacade;
import org.kong.response.ResponseCommon;
import org.kong.user.dto.User;
import org.kong.user.facade.UserFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

  private final AuthFacade authFacade;
  private final UserFacade userFacade;

  @PostMapping("/join")
  public ResponseEntity<ResponseCommon<Object>> join(
      @RequestBody User.Request request, HttpSession httpSession) {
    User.Response user = userFacade.join(request, httpSession);

    ResponseCommon<Object> response =
        ResponseCommon.builder().code(1).msg("로그인 성공").data(user).build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseCommon<Object>> login(
      @RequestBody User.Request request, HttpServletRequest httpServletRequest) {

    User.Response user = authFacade.userLogin(request, httpServletRequest);

    ResponseCommon<Object> response =
        ResponseCommon.builder().code(1).msg("로그인 성공").data(user).build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/logout")
  public ResponseEntity<ResponseCommon<Object>> logout(HttpServletRequest request) {
    
    authFacade.userLogout(request);

    ResponseCommon<Object> response = ResponseCommon.builder().code(1).msg("로그아웃 성공").build();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
