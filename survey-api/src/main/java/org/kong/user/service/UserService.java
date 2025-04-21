package org.kong.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kong.user.dto.User;
import org.kong.user.entity.UserEntity;
import org.kong.user.mapper.UserMapper;
import org.kong.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserEntity findUserById(Integer userId) {
        UserEntity user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return user;
    }

}
