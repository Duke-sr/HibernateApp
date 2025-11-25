package com.duke.notification.controller;

import com.duke.notification.dto.EmailDto;
import com.duke.notification.service.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class EmailController {

    private final MailServiceImpl mailService;

    @PostMapping()
    public ResponseEntity<EmailDto> sendMail(@RequestBody EmailDto request) {
        log.info("Получен запрос на отправку письма: {}", request);
        mailService.sendEmail(request.email(), request.subject(), request.text());
        return ResponseEntity.ok(request);
    }
}