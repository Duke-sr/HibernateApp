package com.duke.hibernate.service;

import com.duke.hibernate.entity.UserEntity;
import com.duke.hibernate.mapper.UserMapper;
import com.duke.hibernate.dto.UserRequestDto;
import com.duke.hibernate.dto.UserResponseDto;
import com.duke.hibernate.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final UserRepository repository;

    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        log.info("Сохранение данных добавленого пользователя: {}", dto);
        var user = mapper.fromDto(dto);
        user.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        var savedUser = saveUser(user);
        return mapper.toDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID пользователя не может быть отрицательным");
        }
        log.info("Поиск данных пользователя c ID: {}", id);
        var user = findUserById(id);
        return mapper.toDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        log.info("Поиск данных пользователя с ID: {} для обновления", id);
        var user = findUserById(id);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        var savedUser = saveUser(user);
        return mapper.toDto(savedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Поиск данных пользователя с ID: {} для удаления", id);
        var user = findUserById(id);
        repository.delete(user);
    }

    private UserEntity saveUser(UserEntity user) {
        return repository.save(user);
    }

    private UserEntity findUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID = " + id + " не найден"));
    }
}