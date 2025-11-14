package com.duke.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.duke.common.dto.KafkaDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Обработка сообщения, переданного из consumer
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    /**
     * JavaMailSender - драйвер SMTP. Spring создает его автоматически при указанных свойствах.
     * -> открывает TCP-соединение с smtp.gmail.com
     * -> AUTH LOGIN
     * -> отправляет письмо
     */
    private final JavaMailSender mailSender;

    public void process(KafkaDto event) {
        if (event == null || event.getEventType() == null) {
            log.warn("Получено некорректное сообщение: {}", event);
            return;
        }

        switch (event.getEventType()) {
            case CREATED -> sendEmail(
                    event.getEmail(),
                    "Type Create",
                    "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
            case DELETED -> sendEmail(
                    event.getEmail(),
                    "Type Delete",
                    "Здравствуйте! Ваш аккаунт был удалён.");
        }
    }

    /**
     * Формирует письмо для отправки. Берет конфигурацию SMTP из application.yml
     */
    public void sendEmail(String email, String subject, String text) {
        try {
            var message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);

            log.info("Письмо отправлено: {}", email);
        } catch (Exception e) {
            log.error("Ошибка при отправке письма: {}", e.getMessage());
        }
    }
}