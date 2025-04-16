package org.kong.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kong.user.dto.User;
import org.kong.user.mapper.UserMapper;
import org.kong.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User.Response findUserById(Integer userId) {
        User.Response user = userRepository.findByUserId(userId).map(userMapper::toUserResponse)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        log.info("사용자 정보: {}", user.getUserId());
        return user;
    }

}
