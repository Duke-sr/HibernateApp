package com.duke.user.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String name;
    private Long age;
    private String email;
}
