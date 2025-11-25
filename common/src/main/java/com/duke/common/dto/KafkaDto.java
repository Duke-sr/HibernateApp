package com.duke.common.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Объект, передаваемый через Kafka между микросервисами.
 * Содержит информацию о пользователе и типе операции.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaDto {
    private Long userId;
    private String email;
    private OperationType operationType;
}