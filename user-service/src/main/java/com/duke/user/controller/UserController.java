package com.duke.user.controller;

import com.duke.user.dto.UserRequestDto;
import com.duke.user.dto.UserResponseDto;
import com.duke.user.hateoas.UserModelAssembler;
import com.duke.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Users API", description = "CRUD операции с пользователями + HATEOAS навигация")
public class UserController {

    private final UserServiceImpl userService;
    private final UserModelAssembler assembler;

    @Operation(
            summary = "Создание пользователя",
            description = "Добавляет нового пользователя и возвращает объект с HATEOAS ссылками",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Пользователь создан",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<EntityModel<UserResponseDto>> createUser(@RequestBody UserRequestDto userRequestDto) {
        log.info("Получен запрос на добавление данных пользователя: {}", userRequestDto);
        var createdUser = userService.createUser(userRequestDto);

        return ResponseEntity
                .created(URI.create("/api/users/" + createdUser.getId()))
                .body(assembler.toModel(createdUser));
    }

    @Operation(
            summary = "Получение пользователя по ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь найден",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDto>> getUser(@PathVariable Long id) {
        log.info("Получен запрос на предоставление данных пользователя c ID: {}", id);
        var user = userService.getUser(id);
        return ResponseEntity.ok(assembler.toModel(user));
    }

    @Operation(
            summary = "Обновление данных пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь обновлён"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDto>> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        log.info("Получен запрос на обновление данных пользователя с ID: {}", id);
        var updatedUser = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(assembler.toModel(updatedUser));
    }

    @Operation(
            summary = "Удаление пользователя",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователь удалён"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Получен запрос на удаление данных пользователя с ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}