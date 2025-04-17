package com.example.OKE.repository;

import com.example.OKE.repository.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    public List<Task> findByLimitDateBetweenOrderByLimitDateDesc(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
