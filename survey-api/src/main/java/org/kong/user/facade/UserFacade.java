package org.kong.user.facade;

import lombok.extern.slf4j.Slf4j;
import org.kong.exception.CustomException;
import org.kong.exception.ErrorCode;
import org.kong.user.dto.User;
import org.kong.user.entity.UserEntity;
import org.kong.user.mapper.UserMapper;
import org.kong.user.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User.Response join(User.Request request, HttpSession httpSession) {
        UserEntity user = userMapper.toEntity(request);

        String encode = bCryptPasswordEncoder.encode(request.getUserPwd());
        user.setUserPwd(encode);
        user = userService.save(user);

        return userMapper.toUserResponse(user);
    }

    public User.Response login(User.Request request, HttpSession session) {
        log.info("===={} ", session);
        UserEntity user = userService.findUserById(request.getUserId());
        
        if (!bCryptPasswordEncoder.matches(request.getUserPwd(), user.getUserPwd())) {
            throw new CustomException(ErrorCode.PASSWORD_FAILD);
        }

        User.Response userResponse = userMapper.toUserResponse(user);

        session.setAttribute("user", userResponse);
        session.setMaxInactiveInterval(3600);

        return userResponse;
    }

}
