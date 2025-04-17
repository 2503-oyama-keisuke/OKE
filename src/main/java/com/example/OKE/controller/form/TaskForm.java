package com.example.OKE.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskForm {
    private int id;
    private String content;
    private short status;
    private LocalDate limit_date;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
