package com.duke.user.controller;

import com.duke.user.dto.UserRequestDto;
import com.duke.user.dto.UserResponseDto;
import com.duke.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        log.info("Получен запрос на добавление данных пользователя: {}", userRequestDto);
        var createdUser = userService.createUser(userRequestDto);
        return ResponseEntity
                .created(URI.create("/api/users/" + createdUser.getId()))
                .body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        log.info("Получен запрос на предоставление данных пользователя c ID: {}", id);
        var getUser = userService.getUser(id);
        return ResponseEntity.ok(getUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        log.info("Получен запрос на обновление данных пользователя с ID: {}", id);
        var updateUser = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Получен запрос на удаление данных пользователя с ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}