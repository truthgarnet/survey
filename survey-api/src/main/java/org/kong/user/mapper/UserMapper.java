package org.kong.user.mapper;

import org.kong.user.dto.User;
import org.kong.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User.Response toUserResponse(UserEntity userEntity);
}
