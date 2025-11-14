package com.duke.user.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.duke.common.dto.KafkaDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserProducer {

    private final KafkaTemplate<String, KafkaDto> kafkaTemplate;

    public void sendUserEvent(KafkaDto message) {
        log.info("Отправка сообщения в Kafka: {}", message);
        kafkaTemplate.send("kafka_message", message);
    }
}