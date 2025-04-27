package org.kong.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kong.exception.CustomException;
import org.kong.exception.ErrorCode;
import org.kong.user.dto.User;
import org.kong.user.entity.UserEntity;
import org.kong.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserEntity findUserById(Integer userId) {
        UserEntity user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return user;
    }

    public UserEntity findUserByIdAndUserByPwd(User.Request request) {
        UserEntity user = userRepository.findByUserIdAndUserPwd(request.getUserId(), request.getUserPwd()).orElseThrow(() -> new CustomException((ErrorCode.USER_NOT_FOUND)));

        return user;
    }

}
