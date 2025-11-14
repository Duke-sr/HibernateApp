package com.duke.user.service;


import com.duke.user.dto.UserRequestDto;
import com.duke.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);

    UserResponseDto getUser(Long id);

    UserResponseDto updateUser(Long id, UserRequestDto dto);

    void deleteUser(Long id);
}