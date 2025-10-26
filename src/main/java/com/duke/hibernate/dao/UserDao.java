package com.duke.hibernate.dao;

import com.duke.hibernate.entity.User;

public interface UserDao {
    void create(User user);

    User get(Long id);

    void update(User user);

    void delete(Long user);
}
