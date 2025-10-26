package com.duke.hibernate.service;

import com.duke.hibernate.entity.User;
import com.duke.hibernate.dao.UserDaoImpl;

public class UserService {
    private final UserDaoImpl usersDao = new UserDaoImpl();

    public void createUser(User user) {
        usersDao.create(user);
    }

    public User getUser(Long id) {
        return usersDao.get(id);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public void deleteUser(Long id) {
        usersDao.delete(id);
    }
}