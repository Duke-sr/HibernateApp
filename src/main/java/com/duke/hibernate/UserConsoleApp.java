package com.duke.hibernate;

import com.duke.hibernate.entity.User;
import com.duke.hibernate.service.UserService;
import com.duke.hibernate.util.HibernateSessionFactoryUtil;

import java.util.Scanner;
import java.time.ZoneOffset;
import java.time.LocalDateTime;

public class UserConsoleApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();

    static void main(String[] args) {
        System.out.println("Консольное приложение");

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> createUser();
                case "2" -> getUsers();
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
        User user = readUserInput(null);
        userService.createUser(user);
        System.out.println("Пользователь добавлен: " + user);
    }

    private static void getUsers() {
        System.out.print("Введите ID пользователя для получения: ");
        Long id = Long.parseLong(scanner.nextLine());
        User existing = userService.getUser(id);
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
        Long id = Long.parseLong(scanner.nextLine());
        User existing = userService.getUser(id);
        if (existing == null) {
            System.out.println("Пользователь с ID " + id + " не найден.");
            return;
        }

        User updated = readUserInput(existing);
        userService.updateUser(updated);
        System.out.println("Пользователь обновлен: " + updated);
    }

    private static void deleteUser() {
        System.out.print("Введите ID пользователя для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());
        userService.deleteUser(id);
        System.out.println("Пользователь c ID " + id + " удалён");
    }

    private static User readUserInput(User user) {
        if (user == null) {
            user = new User();
        }

        System.out.print("Введите имя: ");
        user.setName(scanner.nextLine());

        System.out.print("Введите email: ");
        user.setEmail(scanner.nextLine());

        System.out.print("Введите возраст: ");
        user.setAge(Long.parseLong(scanner.nextLine()));

        user.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        return user;
    }
}