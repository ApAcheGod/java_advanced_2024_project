package ru.otus.java.advanced.PledgeMonitoring.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ExaminationUpdateStatusDto {
    private UUID examinationId;
    private UUID statusId;
}
