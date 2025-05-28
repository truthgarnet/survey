package org.kong.admin.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.kong.admin.dto.AdminLoginRequest;
import org.kong.admin.dto.AdminLoginResponse;
import org.kong.admin.dto.AdminUsersResponse;
import org.kong.admin.entity.AdminEntity;
import org.kong.admin.mapper.AdminMapper;
import org.kong.admin.service.AdminService;
import org.kong.exception.CustomException;
import org.kong.exception.ErrorCode;
import org.kong.survey.dto.PageDto;
import org.kong.user.entity.UserEntity;
import org.kong.user.mapper.UserMapper;
import org.kong.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminFacade {

  private final AdminService adminService;
  private final UserService userService;

  private final AdminMapper adminMapper;
  private final UserMapper userMapper;

 

  public PageDto<AdminUsersResponse> getUsers() {
    Page<UserEntity> users = userService.findAll(0, 10);

    return userMapper.toAdminUsersResponse(users);
  }
}
