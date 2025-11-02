package com.duke.hibernate.service;

import com.duke.hibernate.dao.UserDaoImpl;
import com.duke.hibernate.entity.UserEntity;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final Long USER_ID = 1L;
    private static final Instant FIXED_INSTANT = Instant.EPOCH;

    @Mock
    private UserDaoImpl userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity createUserEntity() {
        return UserEntity.builder()
                .id(USER_ID)
                .name("Ivan")
                .age(30L)
                .email("ivan@google.com")
                .createdAt(FIXED_INSTANT)
                .build();
    }

    /**
     * Тест на добавление данных пользователя
     */
    @Test
    void shouldCreateUser() {
        UserEntity userEntity = createUserEntity();

        userService.createUser(userEntity);

        verify(userDao).create(userEntity);
        verifyNoMoreInteractions(userDao);
    }

    /**
     * Тест на получение данных пользователя по ID
     */
    @Test
    void shouldGetUser() {
        UserEntity userEntitySet = createUserEntity();

        when(userDao.get(USER_ID)).thenReturn(userEntitySet);

        UserEntity userEntityGet = userService.getUser(USER_ID);

        assertEquals(userEntitySet, userEntityGet);
        verify(userDao).get(USER_ID);
        verifyNoMoreInteractions(userDao);
    }

    /**
     * Тест на обновление данных пользователя
     */
    @Test
    void shouldUpdateUser() {
        UserEntity userEntity = createUserEntity();

        userService.updateUser(userEntity);

        verify(userDao).update(userEntity);
        verifyNoMoreInteractions(userDao);
    }

    /**
     * Тест на удаление данных пользователя
     */
    @Test
    void shouldDeleteUser() {
        userService.deleteUser(USER_ID);

        verify(userDao).delete(USER_ID);
        verifyNoMoreInteractions(userDao);
    }

    /**
     * Тест на несуществующего пользователя
     */
    @Test
    void shouldReturnNull() {
        when(userDao.get(USER_ID)).thenReturn(null);

        UserEntity result = userService.getUser(USER_ID);

        assertNull(result);
        verify(userDao).get(USER_ID);
    }
}