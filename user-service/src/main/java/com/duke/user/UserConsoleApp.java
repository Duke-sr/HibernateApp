package com.duke.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.duke.user")
public class UserConsoleApp {
    public static void main(String[] args) {
        SpringApplication.run(UserConsoleApp.class, args);
    }
}