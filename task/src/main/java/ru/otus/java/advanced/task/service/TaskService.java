package ru.otus.java.advanced.task.service;

import ru.otus.java.advanced.task.dto.ChangeStatusDto;
import ru.otus.java.advanced.task.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    Task createTask(UUID contractId);

    Optional<Task> changeStatus(ChangeStatusDto changeStatusDto);

    List<Task> findAll();

    List<Task> findAllByContractId(UUID contractId);
}
