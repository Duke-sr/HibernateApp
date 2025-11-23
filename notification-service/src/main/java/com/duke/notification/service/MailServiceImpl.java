package com.duke.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    /**
     * JavaMailSender - драйвер SMTP. Spring создает его автоматически при указанных свойствах.
     * -> открывает TCP-соединение с smtp.gmail.com
     * -> AUTH LOGIN
     * -> отправляет письмо
     */
    private final JavaMailSender mailSender;

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