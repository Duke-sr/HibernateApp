package com.duke.hibernate.service;

import com.duke.hibernate.dao.UserDaoImpl;
import com.duke.hibernate.entity.UserEntity;

public class UserServiceImpl implements UserService {
    private final UserDaoImpl usersDao = new UserDaoImpl();

    @Override
    public void createUser(UserEntity userEntity) {
        usersDao.create(userEntity);
    }

    @Override
    public UserEntity getUser(Long id) {
        return usersDao.get(id);
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        usersDao.update(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        usersDao.delete(id);
    }
}