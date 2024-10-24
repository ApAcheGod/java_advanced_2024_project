package ru.otus.java.advanced.task.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class TaskDto {
    private UUID id;
    private UUID contractId;
    private UUID taskStatus;
    private String taskStatusName;
}
