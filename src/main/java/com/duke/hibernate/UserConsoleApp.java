package com.duke.hibernate;

import com.duke.hibernate.entity.UserEntity;
import com.duke.hibernate.service.UserService;
import com.duke.hibernate.service.UserServiceImpl;
import com.duke.hibernate.util.HibernateSessionFactoryUtil;

import java.util.Scanner;
import java.time.ZoneOffset;
import java.time.LocalDateTime;

public class UserConsoleApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService UserServiceImpl = new UserServiceImpl();

    static void main() {
        System.out.println("Консольное приложение");

        while (true) {
            printMenu();
            var choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> createUser();
                case "2" -> getUser();
                case "3" -> updateUser();
                case "4" -> deleteUser();
                case "0" -> {
                    HibernateSessionFactoryUtil.shutdown();
                    System.out.println("Завершение приложения...");
                    return;
                }
                default -> System.out.println("Некорректный выбор");
            }
        }
    }

    private static void printMenu() {
        System.out.println("1 - Добавить пользователя");
        System.out.println("2 - Получить пользователя");
        System.out.println("3 - Обновить пользователя");
        System.out.println("4 - Удалить пользователя");
        System.out.println("0 - Выход");
        System.out.print("Выберите действие: ");
    }

    private static void createUser() {
        var userEntity = readUserInput(null);
        UserServiceImpl.createUser(userEntity);
        System.out.println("Пользователь добавлен: " + userEntity);
    }

    private static void getUser() {
        System.out.print("Введите ID пользователя для получения: ");
        var id = Long.parseLong(scanner.nextLine());
        var existing = UserServiceImpl.getUser(id);
        if (existing == null) {
            System.out.println("Пользователь с ID " + id + " не найден.");
        } else {
            System.out.println("Пользователь с ID " + id
                    + "\nИмя пользователя: " + existing.getName()
                    + "\nEmail пользователя: " + existing.getEmail()
                    + "\nВозраст пользователя: " + existing.getAge()
                    + "\nВремя добавления: " + existing.getCreatedAt());
        }
    }

    private static void updateUser() {
        System.out.print("Введите ID пользователя для обновления: ");
        var id = Long.parseLong(scanner.nextLine());
        var existing = UserServiceImpl.getUser(id);
        if (existing == null) {
            System.out.println("Пользователь с ID " + id + " не найден.");
            return;
        }

        var updated = readUserInput(existing);
        UserServiceImpl.updateUser(updated);
        System.out.println("Пользователь обновлен: " + updated);
    }

    private static void deleteUser() {
        System.out.print("Введите ID пользователя для удаления: ");
        var id = Long.parseLong(scanner.nextLine());
        UserServiceImpl.deleteUser(id);
        System.out.println("Пользователь c ID " + id + " удалён");
    }

    private static UserEntity readUserInput(UserEntity userEntity) {
        if (userEntity == null) {
            userEntity = new UserEntity();
        }

        System.out.print("Введите имя: ");
        userEntity.setName(scanner.nextLine());

        System.out.print("Введите email: ");
        userEntity.setEmail(scanner.nextLine());

        System.out.print("Введите возраст: ");
        userEntity.setAge(Long.parseLong(scanner.nextLine()));

        userEntity.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        return userEntity;
    }
}