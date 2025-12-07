package com.duke.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/users")
    public ResponseEntity<String> usersFallback() {
        return ResponseEntity.ok("User service не доступен.");
    }

    @GetMapping("/fallback/notifications")
    public ResponseEntity<String> notificationsFallback() {
        return ResponseEntity.ok("Notification service не доступен.");
    }
}