package ru.otus.java.advanced.PledgeMonitoring.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CreateTaskDto {
    private UUID contractId;
}
