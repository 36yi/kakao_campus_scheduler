package com.example.scheduler.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Schedule {
    private Long id;
    private String task;
    private String author;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}