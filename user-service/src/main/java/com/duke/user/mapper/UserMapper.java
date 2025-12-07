package com.duke.user.mapper;

import com.duke.user.dto.UserRequestDto;
import com.duke.user.dto.UserResponseDto;
import com.duke.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserEntity fromDto(UserRequestDto dto);

    UserResponseDto toDto(UserEntity userEntity);
}