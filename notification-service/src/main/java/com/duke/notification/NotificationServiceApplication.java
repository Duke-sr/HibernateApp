package com.duke.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.duke.notification")
public class NotificationServiceApplication {
    static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}