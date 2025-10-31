package com.duke.hibernate.dao;

import com.duke.hibernate.entity.UserEntity;

/**
 * Интерфейс определят операции CRUD.
 */

public interface UserDao {
    /**
     * Добавление данных для нового пользователя в БД.
     */
    void create(UserEntity userEntity);

    /**
     * Получение данных пользователя по ID.
     */
    UserEntity get(Long id);

    /**
     * Обновление данных существующего пользователя.
     */
    void update(UserEntity userEntity);

    /**
     * Удаление данных пользователя по ID.
     */
    void delete(Long id);
}