package org.kong.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.kong.admin.dto.AdminLoginRequest;
import org.kong.admin.entity.AdminEntity;
import org.kong.admin.repository.AdminRespository;
import org.kong.exception.CustomException;
import org.kong.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

  private final AdminRespository adminRespository;

  public AdminEntity findByAdminId(AdminLoginRequest loginRequest) {
    log.info("관리자 조회");
    AdminEntity adminEntity =
        adminRespository
            .findByAdminId(loginRequest.getAdminId())
            .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));

    return adminEntity;
  }
}
