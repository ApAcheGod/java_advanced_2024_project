package ru.otus.java.advanced.PledgeMonitoring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.java.advanced.PledgeMonitoring.client.DictionaryClient;
import ru.otus.java.advanced.PledgeMonitoring.dto.ExaminationDto;
import ru.otus.java.advanced.PledgeMonitoring.entity.Examination;

@Component
@RequiredArgsConstructor
public class ExaminationMapper {

    private final DictionaryClient dictionaryClient;

    public ExaminationDto toDto(Examination examination) {
        return ExaminationDto
                .builder()
                .id(examination.getId())
                .examinationType(examination.getExaminationType())
                .examinationTypeName(dictionaryClient.getDictionaryById(examination.getExaminationType()).getName())
                .examinationStatus(examination.getExaminationStatus())
                .examinationStatusName(dictionaryClient.getDictionaryById(examination.getExaminationStatus()).getName())
                .contractId(examination.getContractId())
                .pledgeId(examination.getPledgeId())
                .planExaminationDate(examination.getPlanExaminationDate())
                .build();
    }

}
