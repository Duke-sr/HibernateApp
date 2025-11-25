package com.duke.notification.consumer;

import com.duke.notification.service.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.duke.common.dto.KafkaDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Получение сообщения из Kafka и инициирование отправки в Service
 * Когда сообщение появляется в топике, десериализует JSON → KafkaDto и передает в service
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserConsumer {
    private final NotificationServiceImpl notificationService;

    @KafkaListener(
            topics = "kafka_message",
            groupId = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(KafkaDto operation) {
        log.info("Получено сообщение из Kafka: {}", operation);
        notificationService.process(operation);
    }
}