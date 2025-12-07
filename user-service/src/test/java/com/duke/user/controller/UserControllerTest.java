package com.duke.user.controller;

import com.duke.user.dto.UserRequestDto;
import com.duke.user.dto.UserResponseDto;
import com.duke.user.hateoas.UserModelAssembler;
import com.duke.user.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @MockitoBean
    private UserServiceImpl userService;

    @MockitoBean
    private UserModelAssembler assembler;

    /**
     * Чтобы HATEOAS не ломал JSON-поля DTO в тестах
     */
    @BeforeEach
    void setup() {
        when(assembler.toModel(any(UserResponseDto.class)))
                .thenAnswer(invocation -> EntityModel.of(invocation.getArgument(0)));
    }

    @Test
    void shouldCreateUserAndReturn201() throws Exception {
        var requestDto = new UserRequestDto();
        requestDto.setName("Игорь");
        requestDto.setEmail("@gmail.com");
        requestDto.setAge(43L);

        var responseDto = new UserResponseDto();
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

    @Test
    void shouldGetUserAndReturn200() throws Exception {
        var responseDto = new UserResponseDto();
        responseDto.setId(2L);
        responseDto.setName("Аня");
        responseDto.setEmail("@mail.com");
        responseDto.setAge(33L);
        responseDto.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));

        when(userService.getUser(2L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Аня"))
                .andExpect(jsonPath("$.email").value("@mail.com"))
                .andExpect(jsonPath("$.age").value(33));
    }

    @Test
    void shouldGetUserAndReturn404() throws Exception {
        when(userService.getUser(500L))
                .thenThrow(new EntityNotFoundException("Пользователь с ID = 500 не найден"));

        mockMvc.perform(get("/api/users/500"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Пользователь с ID = 500 не найден"));
    }

    @Test
    void shouldGetUserAndReturn400() throws Exception {
        when(userService.getUser(-1L))
                .thenThrow(new IllegalArgumentException("ID пользователя не может быть отрицательным"));

        mockMvc.perform(get("/api/users/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("ID пользователя не может быть отрицательным"));
    }

    @Test
    void shouldUpdateUserAndReturn200() throws Exception {
        var requestDto = new UserRequestDto();
        requestDto.setName("New");
        requestDto.setEmail("new@gmail.com");
        requestDto.setAge(20L);

        var responseDto = new UserResponseDto();
        responseDto.setId(3L);
        responseDto.setName("New");
        responseDto.setEmail("new@gmail.com");
        responseDto.setAge(20L);
        responseDto.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));

        when(userService.updateUser(eq(3L), any(UserRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/users/3")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New"))
                .andExpect(jsonPath("$.email").value("new@gmail.com"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    void shouldUpdateUserAndReturn404() throws Exception {
        when(userService.updateUser(eq(1000L), any(UserRequestDto.class)))
                .thenThrow(new EntityNotFoundException("Пользователь с ID = 1000 не найден"));

        mockMvc.perform(put("/api/users/1000")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(new UserRequestDto())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Пользователь с ID = 1000 не найден"));
    }

    @Test
    void shouldDeleteUserAndReturn204() throws Exception {
        doNothing().when(userService).deleteUser(14L);

        mockMvc.perform(delete("/api/users/14"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(14L);
    }

    @Test
    void shouldDeleteUserAndReturn404() throws Exception {
        doThrow(new EntityNotFoundException("Пользователь с ID = 1111 не найден"))
                .when(userService).deleteUser(1111L);

        mockMvc.perform(delete("/api/users/1111"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Пользователь с ID = 1111 не найден"));
    }
}