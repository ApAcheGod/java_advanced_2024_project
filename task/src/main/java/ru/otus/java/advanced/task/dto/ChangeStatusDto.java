package ru.otus.java.advanced.task.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ChangeStatusDto {
    private UUID id;
    private UUID taskStatus;
}
