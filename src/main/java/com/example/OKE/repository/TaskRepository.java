package com.example.OKE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.OKE.repository.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
