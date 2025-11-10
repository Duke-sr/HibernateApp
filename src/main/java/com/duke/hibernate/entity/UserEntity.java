package com.duke.hibernate.entity;

import lombok.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.Instant;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(columnDefinition = "TEXT")
    @EqualsAndHashCode.Include
    private String name;

    @Column(columnDefinition = "bigint")
    @EqualsAndHashCode.Include
    private Long age;

    @Column(columnDefinition = "TEXT")
    @EqualsAndHashCode.Include
    private String email;

    @Column(columnDefinition = "timestamp")
    private Instant createdAt;
}