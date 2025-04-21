package com.example.OKE.controller.form;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskForm {

    private int id;
    @NotBlank(message="・タスクを入力してください")
    @Length(max=140, message="・タスクは140文字以内で入力してください")
    private String content;
    private Integer status;
    @NotNull(message="・期限を設定してください")
    @FutureOrPresent(message="・無効な日付です")
    private LocalDate limitDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}