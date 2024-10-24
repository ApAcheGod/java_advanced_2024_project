package ru.otus.java.advanced.PledgeMonitoring.service;

import ru.otus.java.advanced.PledgeMonitoring.dto.ExaminationUpdateStatusDto;
import ru.otus.java.advanced.PledgeMonitoring.entity.Examination;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExaminationService {

    List<Examination> findByContractId(UUID id);

    Optional<Examination> updateStatus(ExaminationUpdateStatusDto examinationUpdateStatusDto);
}
