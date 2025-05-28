package org.kong.user.facade;

import lombok.extern.slf4j.Slf4j;

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
        user.setPassword(encode);
        user = userService.save(user);

        return userMapper.toUserResponse(user);
    }

}
