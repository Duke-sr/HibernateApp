package com.duke.hibernate.controller;

import com.duke.hibernate.dto.UserRequestDto;
import com.duke.hibernate.dto.UserResponseDto;
import com.duke.hibernate.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @MockitoBean
    private UserServiceImpl userService;

    /**
     * Тест добавления данных пользователя
     * Ожидаемый возврат кода 201
     */
    @Test
    void shouldCreateUserAndReturn201() throws Exception {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setName("Игорь");
        requestDto.setEmail("@gmail.com");
        requestDto.setAge(43L);

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Игорь");
        responseDto.setEmail("@gmail.com");
        responseDto.setAge(43L);
        responseDto.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));

        when(userService.createUser(any(UserRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/users/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Игорь"))
                .andExpect(jsonPath("$.email").value("@gmail.com"));
    }

    /**
     * Тест получения данных пользователя
     * Ожидаемый возврат кода 200
     */
    @Test
    void shouldGetUserAndReturn200() throws Exception {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(2L);
        responseDto.setName("Аня");
        responseDto.setEmail("@mail.com");
        responseDto.setAge(33L);
        responseDto.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));

        when(userService.getUser(eq(2L))).thenReturn(responseDto);

        mockMvc.perform(get("/api/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Аня"))
                .andExpect(jsonPath("$.email").value("@mail.com"))
                .andExpect(jsonPath("$.age").value(33));
    }

    /**
     * Тест получения данных пользователя
     * Ожидаемый возврат кода 404
     */
    @Test
    void shouldGetUserAndReturn404() throws Exception {
        when(userService.getUser(eq(500L)))
                .thenThrow(new EntityNotFoundException("Пользователь с ID = 500 не найден"));

        mockMvc.perform(get("/api/users/500"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Пользователь с ID = 500 не найден"));
    }

    /**
     * Тест получения данных пользователя
     * Ожидаемый возврат кода 400
     */
    @Test
    void shouldGetUserAndReturn400() throws Exception {
        when(userService.getUser(eq(-1L)))
                .thenThrow(new IllegalArgumentException("ID пользователя не может быть отрицательным"));

        mockMvc.perform(get("/api/users/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("ID пользователя не может быть отрицательным"));
    }

    /**
     * Тест обновления данных пользователя
     * Ожидаемый возврат кода 200
     */
    @Test
    void shouldUpdateUserAndReturn200() throws Exception {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setName("New");
        requestDto.setEmail("new@gmail.com");
        requestDto.setAge(20L);

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(3L);
        responseDto.setName("New");
        responseDto.setEmail("new@gmail.com");
        responseDto.setAge(20L);
        responseDto.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));

        when(userService.updateUser(eq(3L), any(UserRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/users/3")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New"))
                .andExpect(jsonPath("$.email").value("new@gmail.com"))
                .andExpect(jsonPath("$.age").value(20));
    }

    /**
     * Тест обновления данных пользователя
     * Ожидаемый возврат кода 404
     */
    @Test
    void shouldUpdateUserAndReturn404() throws Exception {
        UserRequestDto requestDto = new UserRequestDto();

        when(userService.updateUser(eq(1000L), any(UserRequestDto.class)))
                .thenThrow(new EntityNotFoundException("Пользователь с ID = 1000 не найден"));

        mockMvc.perform(put("/api/users/1000")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Пользователь с ID = 1000 не найден"));
    }

    /**
     * Тест удаление данных пользователя
     * Ожидаемый возврат кода 204
     */
    @Test
    void shouldDeleteUserAndReturn204() throws Exception {
        doNothing().when(userService).deleteUser(eq(14L));

        mockMvc.perform(delete("/api/users/14"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(14L);
    }

    /**
     * Тест удаление данных пользователя
     * Ожидаемый возврат кода 404
     */
    @Test
    void shouldDeleteUserAndReturn404() throws Exception {
        doThrow(new EntityNotFoundException("Пользователь с ID = 1111 не найден"))
                .when(userService).deleteUser(eq(1111L));

        mockMvc.perform(delete("/api/users/1111"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Пользователь с ID = 1111 не найден"));
    }
}