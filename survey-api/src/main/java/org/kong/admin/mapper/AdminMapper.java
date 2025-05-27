package org.kong.admin.mapper;

import org.kong.admin.dto.AdminLoginResponse;
import org.kong.admin.entity.AdminEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminMapper {

  AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

  AdminLoginResponse toAdminLoginResponse(AdminEntity adminEntity);
}
