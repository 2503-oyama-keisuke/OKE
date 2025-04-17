package com.example.OKE.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskForm {

    private int id;
    @NotBlank(message = "投稿内容を入力してください")
    private String content;
    private int status;
    private Date limitDate;
    private Date createdDate;
    private Date updatedDate;
}