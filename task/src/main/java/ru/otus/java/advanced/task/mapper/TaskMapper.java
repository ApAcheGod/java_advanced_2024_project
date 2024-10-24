package ru.otus.java.advanced.task.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.java.advanced.task.client.DictionaryClient;
import ru.otus.java.advanced.task.dto.TaskDto;
import ru.otus.java.advanced.task.entity.Task;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final DictionaryClient dictionaryClient;

    public TaskDto toDto(Task task) {
        return TaskDto
                .builder()
                .id(task.getId())
                .contractId(task.getContractId())
                .taskStatus(task.getTaskStatus())
                .taskStatusName(dictionaryClient.getDictionaryById(task.getTaskStatus()).getName())
                .build();
    }

    public Task toEntity(TaskDto taskDto) {
        return Task
                .builder()
                .id(taskDto.getId())
                .contractId(taskDto.getContractId())
                .taskStatus(taskDto.getTaskStatus())
                .build();
    }
}
