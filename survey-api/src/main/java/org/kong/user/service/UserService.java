package org.kong.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kong.exception.CustomException;
import org.kong.exception.ErrorCode;
import org.kong.user.entity.UserEntity;
import org.kong.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  public Page<UserEntity> findAll(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<UserEntity> users = userRepository.findAll(pageRequest);

    return users;
  }

  public UserEntity findById(Integer id) {
    UserEntity user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    return user;
  }

  public UserEntity findUserId(String userId) {
    UserEntity user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));

    return user;
  }

  public UserEntity save(UserEntity user) {
    user = userRepository.save(user);
    return user;
  }
}
