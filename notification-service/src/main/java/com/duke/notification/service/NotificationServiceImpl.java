package com.duke.notification.service;

import com.duke.common.dto.KafkaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Обработка сообщения, переданного из consumer
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final MailServiceImpl mailService;

    public void process(KafkaDto operation) {

        switch (operation.getOperationType()) {
            case CREATED -> mailService.sendEmail(
                    operation.getEmail(),
                    "Type Create",
                    "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
            case DELETED -> mailService.sendEmail(
                    operation.getEmail(),
                    "Type Delete",
                    "Здравствуйте! Ваш аккаунт был удалён.");
            default -> log.warn("Получен некорректный тип запроса: {}", operation.getOperationType());
        }
    }
}