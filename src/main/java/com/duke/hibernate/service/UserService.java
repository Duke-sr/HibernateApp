package com.duke.hibernate.service;

import com.duke.hibernate.entity.UserEntity;

public interface UserService {
    void createUser(UserEntity userEntity);

    UserEntity getUser(Long id);

    void updateUser(UserEntity userEntity);

    void deleteUser(Long id);
}