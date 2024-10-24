package ru.otus.java.advanced.PledgeMonitoring.service;

import ru.otus.java.advanced.PledgeMonitoring.dto.PledgeDto;

public interface ExaminationCalculationService {

    void calculateExaminations(PledgeDto pledgeDto);
}
