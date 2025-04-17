package com.example.OKE.service;

import com.example.OKE.controller.form.TaskForm;
import com.example.OKE.repository.TaskRepository;
import com.example.OKE.repository.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    /*
     * レコード全件取得処理
     */
    public List<TaskForm> findAllTask() {
        List<Task> results = taskRepository.findAll();
        List<TaskForm> tasks = setTaskForm(results);
        return tasks;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<TaskForm> setReportForm(List<Task> results) {
        List<TaskForm> tasks = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            TaskForm task = new TaskForm();
            Task result = results.get(i);
            task.setId(result.getId());
            task.setContent(result.getContent());
            task.setStatus(result.getStatus());
            task.setLimitDate(result.getLimitDate());
            task.add(task);
        }
        return tasks;
    }
}
