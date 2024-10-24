package ru.otus.java.advanced.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.java.advanced.task.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findAllByContractId(UUID contractId);
}
