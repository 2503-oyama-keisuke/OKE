package com.example.OKE.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(insertable = true, updatable = false)
    private String content;

    @Column(insertable = true, updatable = false)
    private Integer status;

    @Column(name = "limit_date", insertable = true, updatable = false)
    private LocalDateTime limitDate;

    @Column(name = "created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", insertable = false, updatable = true)
    private LocalDateTime updatedDate;
}