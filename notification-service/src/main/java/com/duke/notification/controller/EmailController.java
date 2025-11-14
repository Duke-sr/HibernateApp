package com.duke.notification.controller;

import com.duke.notification.service.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.duke.notification.dto.EmailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class EmailController {

    private final NotificationServiceImpl notificationService;

    @PostMapping()
    public ResponseEntity<EmailDto> sendMail(@RequestBody EmailDto request) {
        log.info("Получен запрос на отправку письма: {}", request);
        notificationService.sendEmail(request.email(), request.subject(), request.text());
        return ResponseEntity.ok(request);
    }
}