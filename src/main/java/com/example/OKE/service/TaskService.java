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

    public List<TaskForm> findByLimitDateRange(LocalDate start, LocalDate end, Integer status, String content) {
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
            endDateTime = LocalDate.of(2100, 12, 31).atTime(23, 59, 59);
        }
        if (status != null && !StringUtils.isEmpty(content)) {
            List<Task> results = taskRepository.findByLimitDateBetweenAndStatusAndContentOrderByLimitDateAsc(startDateTime, endDateTime, status, content);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        } else if (status != null) {
            List<Task> results = taskRepository.findByLimitDateBetweenAndStatusOrderByLimitDateAsc(startDateTime, endDateTime, status);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        } else if (!StringUtils.isBlank(content)) {
            List<Task> results = taskRepository.findByLimitDateBetweenAndContentOrderByLimitDateAsc(startDateTime, endDateTime, content);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        } else {
            List<Task> results = taskRepository.findByLimitDateBetweenOrderByLimitDateAsc(startDateTime, endDateTime);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        }
    }

    public void saveTask(TaskForm reqTask) {
        Task saveTask = setTaskEntity(reqTask);
        taskRepository.save(saveTask);
    }

    public TaskForm editTask(Integer id) {
        List<Task> results = new ArrayList<>();
        results.add((Task) taskRepository.findById(id).orElse(null));
        List<TaskForm> tasks = new ArrayList<>();
        if (results.get(0) == null) {
            tasks.add(null);
        } else {
            tasks = setTaskForm(results);
        }
        return tasks.get(0);
    }

    /*
     * タスク削除
     */
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    /*
     * ステータス変更
     */
    public void saveStatus(Integer id, Integer status) {

        Task task = taskRepository.findById(id).orElseThrow();
        task.setStatus(status);
        taskRepository.save(task);
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
            task.setLimitDate(result.getLimitDate().toLocalDate());
            tasks.add(task);
        }
        return tasks;
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Task setTaskEntity(TaskForm reqTask) {
        Task task = new Task();
        task.setId(reqTask.getId());
        task.setContent(reqTask.getContent());
        task.setStatus(reqTask.getStatus());
        task.setLimitDate(reqTask.getLimitDate().atTime(0, 0, 0));
        return task;
    }
}
