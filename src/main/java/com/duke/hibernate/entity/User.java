package com.duke.hibernate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(columnDefinition = "bigint")
    private Long age;

    @Column(columnDefinition = "TEXT")
    private String email;

    @Column(columnDefinition = "timestamp")
    private Instant createdAt;
}