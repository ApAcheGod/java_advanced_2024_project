package ru.otus.java.advanced.PledgeMonitoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.otus.java.advanced.PledgeMonitoring.dto.PledgeDto;

@Component
@Slf4j
@RequiredArgsConstructor
public class PledgeMonitoringRequestListener {

    private final ObjectMapper objectMapper;
    private final ExaminationCalculationService examinationCalculationService;

    @KafkaListener(topics = "${pledge.monitoring.request.topic}", groupId = "monitoring")
    public void listen(@Payload String message) {
        log.info("Received message: {}", message);
        try {
            PledgeDto pledgeDto = objectMapper.readValue(message, PledgeDto.class);
            examinationCalculationService.calculateExaminations(pledgeDto);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

}
