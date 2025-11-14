package com.duke.common.dto;

import lombok.*;

/**
 * Объект, передаваемый через Kafka между микросервисами.
 * Содержит информацию о пользователе и типе операции.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaDto {
    private Long userId;
    private String email;
    private EventType eventType;
}