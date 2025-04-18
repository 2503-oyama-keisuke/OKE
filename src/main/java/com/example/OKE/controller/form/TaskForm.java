package com.example.OKE.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class TaskForm {

    private int id;
    private String content;
    private Integer status;
    private LocalDate limitDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}