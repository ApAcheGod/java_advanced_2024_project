package ru.otus.java.advanced.PledgeMonitoring.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.PledgeMonitoring.dto.ExaminationUpdateStatusDto;
import ru.otus.java.advanced.PledgeMonitoring.entity.Examination;
import ru.otus.java.advanced.PledgeMonitoring.repository.ExaminationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExaminationServiceImpl implements ExaminationService {

    private final ExaminationRepository examinationRepository;

    @Override
    public List<Examination> findByContractId(UUID id) {
        return examinationRepository.findAllByContractId(id);
    }

    @Override
    public Optional<Examination> updateStatus(ExaminationUpdateStatusDto examinationUpdateStatusDto) {
        Optional<Examination> optionalExamination = examinationRepository.findById(examinationUpdateStatusDto.getExaminationId());
        if (optionalExamination.isPresent()) {
            Examination examination = optionalExamination.get();
            examination.setExaminationStatus(examinationUpdateStatusDto.getStatusId());
            return Optional.of(examinationRepository.save(examination));
        }
        return Optional.empty();
    }
}
