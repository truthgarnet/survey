package org.kong.auth.facade;

import org.kong.admin.dto.AdminLoginRequest;
import org.kong.admin.dto.AdminLoginResponse;
import org.kong.admin.entity.AdminEntity;
import org.kong.admin.mapper.AdminMapper;
import org.kong.admin.service.AdminService;
import org.kong.auth.CustomAdminDetails;
import org.kong.auth.CustomUserDetails;
import org.kong.exception.CustomException;
import org.kong.exception.ErrorCode;
import org.kong.user.dto.User;
import org.kong.user.entity.UserEntity;
import org.kong.user.mapper.UserMapper;
import org.kong.user.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacade {
    
    private final UserService userService;
    private final UserMapper userMapper;
    private final AdminService adminService;
    private final AdminMapper adminMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public User.Response userLogin(User.Request request, HttpServletRequest httpServletRequest) {
        UserEntity user = userService.findUserName(request.getUserName());
        
        if (!bCryptPasswordEncoder.matches(request.getUserPwd(), user.getUserPwd())) {
            log.debug("패스워드가 잘못 되었습니다.");
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        User.Response userResponse = userMapper.toUserResponse(user);

        // 1. Authentication 생성
        UserDetails userDetails = new CustomUserDetails(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // 2. SecurityContext에 주입
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        // 3. 세션에 SecurityContext 저장
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context); // Authentication 객체 저장

        return userResponse;
    }

    public AdminLoginResponse adminLogin(AdminLoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        AdminEntity admin = adminService.findByAdminId(loginRequest);
    
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
          log.debug("패스워드가 잘못 되었습니다.");
          throw new CustomException(ErrorCode.LOGIN_FAILED);
        }
    
        // 1. Authentication 생성
        UserDetails adminDetails = new CustomAdminDetails(admin);
        Authentication auth = new UsernamePasswordAuthenticationToken(adminDetails, null, adminDetails.getAuthorities());

        // 2. SecurityContext에 주입
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        // 3. 세션에 SecurityContext 저장
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context); // Authentication 객체 저장
     
        return adminMapper.toAdminLoginResponse(admin);
      }

    public void userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
          session.invalidate();
        }
    }
}
