package com.duke.hibernate.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String name;
    private Long age;
    private String email;
}
