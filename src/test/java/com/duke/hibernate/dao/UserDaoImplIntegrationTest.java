package com.duke.hibernate.dao;

import com.duke.hibernate.entity.UserEntity;
import com.duke.hibernate.util.HibernateSessionFactoryUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.ZoneOffset;
import java.util.Properties;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
class UserDaoImplIntegrationTest {

    private static UserDao userDao;
    private static UserEntity testUser;

    private static final Logger log = LoggerFactory.getLogger(UserDaoImplIntegrationTest.class);

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testDB")
            .withUsername("test")
            .withPassword("test");

    @BeforeAll
    static void setUp() {
        postgres.start();

        var props = new Properties();
        props.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        props.setProperty("hibernate.connection.username", postgres.getUsername());
        props.setProperty("hibernate.connection.password", postgres.getPassword());
        props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        props.setProperty("hibernate.show_sql", "true");

        HibernateSessionFactoryUtil.initSessionFactory(props);

        userDao = new UserDaoImpl();
    }

    @BeforeEach
    void setUpTestUser() {
        testUser = UserEntity.builder()
                .name("Test_User")
                .age(30L)
                .email("user@test.com")
                .createdAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .build();

        userDao.create(testUser);
        assertNotNull(testUser.getId(), "ID должен быть сгенерирован");
    }

    @AfterEach
    void tearDownTestUser() {
        if (testUser != null && testUser.getId() != null) {
            try {
                userDao.delete(testUser.getId());
            } catch (Exception e) {
                log.error("Ошибка при удалении тестового пользователя: {}", e.getMessage());
            }
        }
    }

    @AfterAll
    static void tearDown() {
        HibernateSessionFactoryUtil.shutdown();
    }

    /**
     * Тест на добавление данных пользователя
     */
    @Test
    void shouldCreateUser() {
        var fromDb = userDao.get(testUser.getId());

        assertNotNull(fromDb);

        assertEquals(testUser.getId(), fromDb.getId());
        assertEquals(testUser.getName(), fromDb.getName());
        assertEquals(testUser.getAge(), fromDb.getAge());
        assertEquals(testUser.getEmail(), fromDb.getEmail());
        assertEquals(testUser.getCreatedAt(), fromDb.getCreatedAt());
    }

    /**
     * Тест на получение данных пользователя по ID
     */
    @Test
    void shouldGetUserById() {
        var userFromDB = userDao.get(testUser.getId());

        assertNotNull(userFromDB);

        assertEquals(testUser.getId(), userFromDB.getId());
        assertEquals(testUser.getName(), userFromDB.getName());
        assertEquals(testUser.getAge(), userFromDB.getAge());
        assertEquals(testUser.getEmail(), userFromDB.getEmail());
        assertEquals(testUser.getCreatedAt(), userFromDB.getCreatedAt());
    }

    /**
     * Тест на обновление существующих данных пользователя
     */
    @Test
    void shouldUpdateUser() {
        var original = userDao.get(testUser.getId());

        var id = original.getId();
        assertNotNull(id);

        UserEntity updated = UserEntity.builder()
                .id(id)
                .name("Updated_User")
                .age(31L)
                .email("updated_user@test.com")
                .createdAt(LocalDateTime.now().toInstant(ZoneOffset.UTC).plusSeconds(100))
                .build();

        userDao.update(updated);

        var fromDb = userDao.get(id);
        assertEquals(updated.getName(), fromDb.getName());
        assertEquals(updated.getAge(), fromDb.getAge());
        assertEquals(updated.getEmail(), fromDb.getEmail());
        assertEquals(updated.getCreatedAt(), fromDb.getCreatedAt());
    }

    /**
     * Тест на удаление пользователя
     */
    @Test
    void shouldDeleteUser() {
        var id = testUser.getId();

        assertNotNull(userDao.get(id));

        userDao.delete(id);

        assertNull(userDao.get(id));
    }

    /**
     * Тест на получение данных несуществующего пользователя
     */
    @Test
    void shouldGetNonExistUser() {
        assertNull(userDao.get(Long.MAX_VALUE));
    }
}