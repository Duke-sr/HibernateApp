package com.duke.hibernate.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private Long age;
    private String email;
    private Instant createdAt;
}