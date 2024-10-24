package ru.otus.java.advanced.PledgeMonitoring.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class ExaminationDto {
    private UUID id;
    private UUID contractId;
    private UUID pledgeId;
    private UUID examinationType;
    private String examinationTypeName;
    private UUID examinationStatus;
    private String examinationStatusName;
    private LocalDate planExaminationDate;
}
