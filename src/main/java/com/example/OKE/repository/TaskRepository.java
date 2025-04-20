package com.example.OKE.repository;

import com.example.OKE.repository.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByLimitDateBetweenAndStatusAndContentOrderByLimitDateAsc(
            LocalDateTime start,
            LocalDateTime end,
            Integer status,
            String content
    );

    List<Task> findByLimitDateBetweenAndStatusOrderByLimitDateAsc(
            LocalDateTime start,
            LocalDateTime end,
            Integer status
    );

    List<Task> findByLimitDateBetweenAndContentOrderByLimitDateAsc(
            LocalDateTime start,
            LocalDateTime end,
            String content
    );

    List<Task> findByLimitDateBetweenOrderByLimitDateAsc(
            LocalDateTime start,
            LocalDateTime end
    );
}
