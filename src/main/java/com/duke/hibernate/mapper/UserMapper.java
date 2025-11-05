package com.duke.hibernate.mapper;

import com.duke.hibernate.dto.UserRequestDto;
import com.duke.hibernate.dto.UserResponseDto;
import com.duke.hibernate.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserEntity fromDto(UserRequestDto dto);

    UserResponseDto toDto(UserEntity userEntity);
}