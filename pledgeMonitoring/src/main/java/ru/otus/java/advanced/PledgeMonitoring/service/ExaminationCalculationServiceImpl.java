package ru.otus.java.advanced.PledgeMonitoring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.PledgeMonitoring.client.ContractServiceClient;
import ru.otus.java.advanced.PledgeMonitoring.client.DictionaryClient;
import ru.otus.java.advanced.PledgeMonitoring.dto.ContractDto;
import ru.otus.java.advanced.PledgeMonitoring.dto.PledgeDto;
import ru.otus.java.advanced.PledgeMonitoring.entity.Examination;
import ru.otus.java.advanced.PledgeMonitoring.entity.MonitoringPattern;
import ru.otus.java.advanced.PledgeMonitoring.repository.ExaminationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExaminationCalculationServiceImpl implements ExaminationCalculationService {

    private final MonitoringPatternCache monitoringPatternCache;
    private final ExaminationRepository examinationRepository;
    private final ContractServiceClient contractServiceClient;
    private final DictionaryClient dictionaryClient;

    @Override
    public void calculateExaminations(PledgeDto pledgeDto) {
        String examinationPattern = getExaminationPattern(pledgeDto);
        log.info("Examination pattern: {}", examinationPattern);
        Optional<MonitoringPattern> optionalPattern = monitoringPatternCache.getMonitoringPattern(examinationPattern);
        optionalPattern.ifPresentOrElse(monitoringPattern -> {
                    List<Examination> newExaminations = createNewExaminations(pledgeDto, monitoringPattern);
                    log.info("MonitoringPattern was found {}", monitoringPattern.getPattern());
                    List<Examination> currentExaminations = examinationRepository.findAllByPledgeIdAndExcludeStatus(
                    pledgeDto.getId(),
                    Set.of(UUID.fromString(dictionaryClient.getDictionaryByCode("Examination_Status_Closed").getId()),
                            UUID.fromString(dictionaryClient.getDictionaryByCode("Examination_Status_Cancelled").getId()
                            )));
            if (currentExaminations.isEmpty()) {
                examinationRepository.saveAll(newExaminations);
            } else {
                List<Examination> forUpdate = recalculateExaminations(currentExaminations, newExaminations);
                examinationRepository.saveAll(forUpdate);
            }},
                () -> log.error("MonitoringPattern wasn't found for pledgeObject {}", pledgeDto.getId()));
    }

    private List<Examination> createNewExaminations(PledgeDto pledgeDto, MonitoringPattern monitoringPattern) {
                return List.of(
                        createPriceExamination(pledgeDto, monitoringPattern),
                        createDocumentaryExamination(pledgeDto, monitoringPattern)
                );
    }

    private List<Examination> recalculateExaminations(List<Examination> allByPledgeId, List<Examination> newExaminations) {
        List<Examination> forUpdate = new ArrayList<>();
        for (Examination examination : allByPledgeId) {
            for (Examination newExamination : newExaminations) {
                if (!examination.getMonitoringPattern().equals(newExamination.getMonitoringPattern())) {
                    forUpdate.add(examination);
                    break;
                } else {
                    if (examination.getId().equals(newExamination.getExaminationType())
                        && examination.getPlanExaminationDate().equals(newExamination.getPlanExaminationDate())) {
                        forUpdate.add(examination);
                    } else if (examination.getExaminationType().equals(newExamination.getExaminationType())
                            && !examination.getPlanExaminationDate().equals(newExamination.getPlanExaminationDate())) {
                        forUpdate.add(newExamination);
                    }
                }
            }
        }
        return forUpdate;
    }

    private Examination createPriceExamination(PledgeDto pledgeDto, MonitoringPattern monitoringPattern) {
        Examination examination = new Examination();
        examination.setPledgeId(pledgeDto.getId());
        examination.setContractId(pledgeDto.getContractId());
        examination.setMonitoringPattern(monitoringPattern);
        examination.setExaminationStatus(UUID.fromString(dictionaryClient.getDictionaryByCode("Examination_Status_New").getId()));
        examination.setExaminationType(UUID.fromString(dictionaryClient.getDictionaryByCode("Examination_Type_Price").getId()));
        examination.setPlanExaminationDate(createPlanExaminationDate(pledgeDto, monitoringPattern, "Examination_Type_Price"));
        return examination;
    }

    private LocalDate createPlanExaminationDate(PledgeDto pledgeDto, MonitoringPattern monitoringPattern, String examinationType) {
        Optional<Examination> lastExamination = getLastExamination(pledgeDto, examinationType);
        if (lastExamination.isPresent()) {
            return lastExamination.get().getPlanExaminationDate().plusDays(monitoringPattern.getPriceBaseMonitoringPeriod());
        } else {
            ResponseEntity<ContractDto> contract = contractServiceClient.getContract(pledgeDto.getContractId());
            return contract.getBody().getCreatedAt().toLocalDate().plusDays(monitoringPattern.getPriceBaseMonitoringPeriod());
        }
    }

    private Examination createDocumentaryExamination(PledgeDto pledgeDto, MonitoringPattern monitoringPattern) {
        Examination examination = new Examination();
        examination.setPledgeId(pledgeDto.getId());
        examination.setContractId(pledgeDto.getContractId());
        examination.setMonitoringPattern(monitoringPattern);
        examination.setExaminationStatus(UUID.fromString(dictionaryClient.getDictionaryByCode("Examination_Status_New").getId()));
        examination.setExaminationType(UUID.fromString(dictionaryClient.getDictionaryByCode("Examination_Type_Document").getId()));
        examination.setPlanExaminationDate(createPlanExaminationDate(pledgeDto, monitoringPattern, "Examination_Type_Document"));
        return examination;
    }

    private Optional<Examination> getLastExamination(PledgeDto pledgeDto, String examinationType) {
        return examinationRepository.findLastByPledgeIdAndExaminationStatus(
                pledgeDto.getId(),
                UUID.fromString(dictionaryClient.getDictionaryByCode(examinationType).getId()),
                UUID.fromString(dictionaryClient.getDictionaryByCode("Examination_Status_Closed").getId())
        );
    }

    private String getExaminationPattern(PledgeDto pledgeDto) {
        StringJoiner stringJoiner = new StringJoiner("|");
        if (pledgeDto.getPledgeCost().compareTo(BigDecimal.valueOf(1_000_000)) > 0) {
            stringJoiner.add("1");
        } else {
            stringJoiner.add("0");
        }

        if (pledgeDto.getEstimatedCost().compareTo(BigDecimal.valueOf(1_000_000)) > 0) {
            stringJoiner.add("1");
        } else {
            stringJoiner.add("0");
        }

        if (pledgeDto.getContractCost().compareTo(BigDecimal.valueOf(1_000_000)) > 0) {
            stringJoiner.add("1");
        } else {
            stringJoiner.add("0");
        }
        stringJoiner.add(pledgeDto.getPledgeTypeName());
        stringJoiner.add(pledgeDto.getQualityName());
        stringJoiner.add(pledgeDto.getLiquidityName());
        return stringJoiner.toString();
    }
}
