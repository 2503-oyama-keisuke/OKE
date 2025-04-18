package com.example.OKE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.OKE.repository.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
//    List<Task> findByLimitDateBetweenOrderByLimitDateDesc(startDateTime, endDateTime);
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
