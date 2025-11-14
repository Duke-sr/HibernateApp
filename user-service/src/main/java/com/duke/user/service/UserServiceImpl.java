package com.duke.user.service;

import com.duke.common.dto.EventType;
import com.duke.common.dto.KafkaDto;
import com.duke.user.dto.UserRequestDto;
import com.duke.user.dto.UserResponseDto;
import com.duke.user.entity.UserEntity;
import com.duke.user.mapper.UserMapper;
import com.duke.user.producer.UserProducer;
import com.duke.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final UserRepository repository;
    private final UserProducer eventProducer;

    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        log.info("Сохранение данных добавленого пользователя: {}", dto);
        var user = mapper.fromDto(dto);
        user.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        var savedUser = saveUser(user);

        var event = new KafkaDto(savedUser.getId(), savedUser.getEmail(), EventType.CREATED);
        eventProducer.sendUserEvent(event);
        log.info("Отправлено сообщение о создании пользователя {}", event);

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

        var event = new KafkaDto(user.getId(), user.getEmail(), EventType.DELETED);
        eventProducer.sendUserEvent(event);
        log.info("Отправлено сообщение о удалении пользователя {}", event);
    }

    private UserEntity saveUser(UserEntity user) {
        return repository.save(user);
    }

    private UserEntity findUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID = " + id + " не найден"));
    }
}