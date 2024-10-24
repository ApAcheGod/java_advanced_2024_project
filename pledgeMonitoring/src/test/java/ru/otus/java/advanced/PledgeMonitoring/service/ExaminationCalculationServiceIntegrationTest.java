package ru.otus.java.advanced.PledgeMonitoring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.advanced.proto.DictionaryResponse;
import ru.otus.java.advanced.PledgeMonitoring.client.ContractServiceClient;
import ru.otus.java.advanced.PledgeMonitoring.client.DictionaryClient;
import ru.otus.java.advanced.PledgeMonitoring.dto.ContractDto;
import ru.otus.java.advanced.PledgeMonitoring.dto.PledgeDto;
import ru.otus.java.advanced.PledgeMonitoring.entity.Examination;
import ru.otus.java.advanced.PledgeMonitoring.entity.MonitoringPattern;
import ru.otus.java.advanced.PledgeMonitoring.repository.ExaminationRepository;
import ru.otus.java.advanced.PledgeMonitoring.repository.MonitoringPatterRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;


@ContextConfiguration(classes = {ExaminationCalculationServiceImpl.class, MonitoringPatternCacheImpl.class})
@DataJpaTest
@EnableJpaRepositories(basePackages = "ru.otus.java.advanced.PledgeMonitoring.repository")
@EntityScan(basePackages = "ru.otus.java.advanced.PledgeMonitoring.entity")
@Sql(scripts = "classpath:data.sql")
class ExaminationCalculationServiceIntegrationTest {

    @Autowired
    private ExaminationCalculationService examinationCalculationService;

    @Autowired
    MonitoringPatternCache monitoringPatternCache;

    @Autowired
    MonitoringPatterRepository monitoringPatterRepository;

    @Autowired
    ExaminationRepository examinationRepository;

    @MockBean
    ContractServiceClient contractServiceClient;

    @MockBean
    DictionaryClient dictionaryClient;

    PledgeDto pledgeDto = PledgeDto
            .builder()
            .id(UUID.fromString("5dbca1ec-104c-4ac7-8c48-58648f6957aa"))
            .name("name")
            .contractId(UUID.fromString("63a3d137-673f-49c8-8327-952776eed4ac"))
            .pledgeCost(BigDecimal.valueOf(10.00))
            .pledgeType(UUID.fromString("1e163bba-5e03-42d4-974d-87cbf2a6cddd"))
            .pledgeTypeName("Офисные здания")
            .contractCost(BigDecimal.valueOf(10.00))
            .estimatedCost(BigDecimal.valueOf(10.00))
            .liquidityType(UUID.fromString("c0241a63-b189-42c5-880e-58ab5f73a08e"))
            .liquidityName("Низкая")
            .qualityType(UUID.fromString("eeb01a6c-0d20-4146-a8db-76dfb86c70d9"))
            .qualityName("1 Категория качества")
            .build();


    MonitoringPattern mockMonitoringPattern = MonitoringPattern
            .builder()
            .id(UUID.fromString("1730e0bf-00e9-4052-8fdf-065368eab3c5"))
            .pattern("0|0|0|Офисные здания|1 Категория качества|Низкая")
            .documentFirstMonitoringPeriod(20L)
            .documentBaseMonitoringPeriod(45L)
            .priceFirstMonitoringPeriod(35L)
            .priceBaseMonitoringPeriod(70L)
            .build();

    DictionaryResponse examinationStatusNew = DictionaryResponse
            .newBuilder()
            .setId("1730e0bf-00e9-4052-8fdf-065368eab3c5")
            .setCategoryId("0c90028e-0eb3-4b00-8a20-d4fee65276b9")
            .setCode("Examination_Status_New")
            .setBCode("New")
            .setName("Новый")
            .setDeleted(false)
            .build();

    DictionaryResponse examinationStatusClosed = DictionaryResponse
            .newBuilder()
            .setId("810d2cc2-81ae-4c79-a741-5cfe0fe286f4")
            .setCategoryId("0c90028e-0eb3-4b00-8a20-d4fee65276b9")
            .setCode("Examination_Status_Closed")
            .setBCode("Closed")
            .setName("Закрыт")
            .setDeleted(false)
            .build();


    DictionaryResponse examinationStatusCancelled = DictionaryResponse
            .newBuilder()
            .setId("b71d76d6-8091-4ba4-9a61-3e43f91f2959")
            .setCategoryId("0c90028e-0eb3-4b00-8a20-d4fee65276b9")
            .setCode("Examination_Status_Cancelled")
            .setBCode("Cancelled")
            .setName("Отменен")
            .setDeleted(false)
            .build();

    DictionaryResponse examinationTypePrice = DictionaryResponse
            .newBuilder()
            .setId("c9e84e4f-d871-4180-b849-9e97e195748b")
            .setCategoryId("8132da4d-4a68-40c2-bb37-b13d7c6109a3")
            .setCode("Examination_Type_Price")
            .setBCode("Price")
            .setName("Проверка стоимости")
            .setDeleted(false)
            .build();

    DictionaryResponse examinationTypeDocument = DictionaryResponse
            .newBuilder()
            .setId("8c3d2590-d281-4cc4-8d88-08cb0fbff880")
            .setCategoryId("8132da4d-4a68-40c2-bb37-b13d7c6109a3")
            .setCode("Examination_Type_Document")
            .setBCode("Document")
            .setName("Проверка документов")
            .setDeleted(false)
            .build();

    ContractDto contractDto = ContractDto
            .builder()
            .id(UUID.fromString("63a3d137-673f-49c8-8327-952776eed4ac"))
            .createdAt(LocalDateTime.now())
            .build();

    @Test
    @DisplayName("Сохранение новых проверок")
    void saveNewExaminationsTest() {

        PledgeDto newPledgeDto = PledgeDto
                .builder()
                .id(UUID.fromString("5dbca1ec-104c-4ac7-8c48-58648f6957aa"))
                .name("name")
                .contractId(UUID.fromString("63a3d137-673f-49c8-8327-952776eed4ac"))
                .pledgeCost(BigDecimal.valueOf(10.00))
                .pledgeType(UUID.fromString("1e163bba-5e03-42d4-974d-87cbf2a6cddd"))
                .pledgeTypeName("Офисные здания")
                .contractCost(BigDecimal.valueOf(10.00))
                .estimatedCost(BigDecimal.valueOf(10.00))
                .liquidityType(UUID.fromString("c0241a63-b189-42c5-880e-58ab5f73a08e"))
                .liquidityName("Низкая")
                .qualityType(UUID.fromString("eeb01a6c-0d20-4146-a8db-76dfb86c70d9"))
                .qualityName("1 Категория качества")
                .build();

        when(dictionaryClient.getDictionaryByCode("Examination_Status_Closed")).thenReturn(examinationStatusClosed);
        when(dictionaryClient.getDictionaryByCode("Examination_Status_Cancelled")).thenReturn(examinationStatusCancelled);
        when(dictionaryClient.getDictionaryByCode("Examination_Status_New")).thenReturn(examinationStatusNew);
        when(dictionaryClient.getDictionaryByCode("Examination_Type_Price")).thenReturn(examinationTypePrice);
        when(dictionaryClient.getDictionaryByCode("Examination_Type_Document")).thenReturn(examinationTypeDocument);

        when(contractServiceClient.getContract(newPledgeDto.getContractId())).thenReturn(ResponseEntity.ok(contractDto));

        List<Examination> allByPledgeIdAndExcludeStatus = examinationRepository.findAllByPledgeIdAndExcludeStatus(newPledgeDto.getId(),
                Set.of(UUID.fromString(examinationStatusClosed.getId()),
                        UUID.fromString(examinationStatusCancelled.getId())));

        Assertions.assertEquals(0, allByPledgeIdAndExcludeStatus.size());
        examinationCalculationService.calculateExaminations(newPledgeDto);

        List<Examination> examinations = examinationRepository.findAllByPledgeIdAndExcludeStatus(newPledgeDto.getId(),
                Set.of(UUID.fromString(examinationStatusClosed.getId()),
                        UUID.fromString(examinationStatusCancelled.getId())));


        Assertions.assertEquals(2, examinations.size());
    }

}