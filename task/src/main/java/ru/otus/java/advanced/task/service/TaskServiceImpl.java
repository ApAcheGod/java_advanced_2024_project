package ru.otus.java.advanced.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.task.client.DictionaryClient;
import ru.otus.java.advanced.task.dto.ChangeStatusDto;
import ru.otus.java.advanced.task.entity.Task;
import ru.otus.java.advanced.task.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final DictionaryClient dictionaryClient;

    @Override
    public Task createTask(UUID contractId) {
        return taskRepository.save(Task
                .builder()
                .taskStatus(UUID.fromString(dictionaryClient.getDictionaryByCode("Task_Status_New").getId()))
                .contractId(contractId)
                .build());
    }

    @Override
    public Optional<Task> changeStatus(ChangeStatusDto changeStatusDto) {
        Optional<Task> optionalTask = taskRepository.findById(changeStatusDto.getId());
        if (optionalTask.isPresent()) {
            optionalTask.get().setTaskStatus(changeStatusDto.getTaskStatus());
            return Optional.of(taskRepository.save(optionalTask.get()));
        }
        return optionalTask;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findAllByContractId(UUID contractId) {
        return taskRepository.findAllByContractId(contractId);
    }
}
