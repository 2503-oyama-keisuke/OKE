package com.example.OKE.service;

import com.example.OKE.controller.form.TaskForm;
import com.example.OKE.repository.TaskRepository;
import com.example.OKE.repository.entity.Task;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    /*
     * レコード全件取得処理
     */

    public List<TaskForm> findByLimitDateRange(LocalDate start, LocalDate end, Short status, String content) {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;

        if (start != null) {
            startDateTime = start.atStartOfDay();
        } else {
            startDateTime = LocalDate.of(2020, 1, 1).atStartOfDay();
        }
        if (end != null) {
            endDateTime = end.atTime(23, 59, 59);
        } else {
            endDateTime = LocalDate.of(2030, 12, 31).atStartOfDay();
        }
        if (status != null && !StringUtils.isEmpty(content)) {

        }

//        List<Task> results = taskRepository.findByLimitDateBetweenOrderByLimitDateDesc(startDateTime, endDateTime);
        List<Task> results = taskRepository.findAll();
        List<TaskForm> tasks = setTaskForm(results);
        return tasks;
    }

    /*
     * タスク削除
     */
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<TaskForm> setTaskForm(List<Task> results) {
        List<TaskForm> tasks = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            TaskForm task = new TaskForm();
            Task result = results.get(i);
            task.setId(result.getId());
            task.setContent(result.getContent());
            task.setStatus(result.getStatus());
            task.setLimitDate(result.getLimitDate());
            tasks.add(task);
        }
        return tasks;

    }
}
