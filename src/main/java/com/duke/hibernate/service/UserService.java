package com.duke.hibernate.service;


import com.duke.hibernate.dto.UserRequestDto;
import com.duke.hibernate.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);

    UserResponseDto getUser(Long id);

    UserResponseDto updateUser(Long id, UserRequestDto dto);

    void deleteUser(Long id);
}