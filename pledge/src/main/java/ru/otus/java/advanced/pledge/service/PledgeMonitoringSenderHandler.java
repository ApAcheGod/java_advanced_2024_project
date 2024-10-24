package ru.otus.java.advanced.pledge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.core.GenericHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.pledge.dto.PledgeDto;
import ru.otus.java.advanced.pledge.entity.Pledge;
import ru.otus.java.advanced.pledge.entity.SentLog;
import ru.otus.java.advanced.pledge.repository.SentLogRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PledgeMonitoringSenderHandler implements GenericHandler<PledgeDto> {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SentLogRepository sentLogRepository;
    private final ObjectMapper objectMapper;

    @Value("${pledge.monitoring.request.topic}")
    private String topic;

    @Override
    public Object handle(PledgeDto payload, MessageHeaders headers) {
        try {
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(payload))
                    .whenComplete(
                            (result, ex) -> {
                                if (ex == null) {
                                    log.info(
                                            "message id: {} was sent, offset: {}",
                                            payload.getId(),
                                            result.getRecordMetadata().offset());
                                    sentLogRepository.save(SentLog
                                            .builder()
                                                    .pledgeId(payload.getId())
                                                    .status(100)
                                            .build());
                                    log.info("SentLog with pledgeId {} was saved ", payload.getId());
                                } else {
                                    log.error("message id: {} was not sent", payload.getId(), ex);
                                    sentLogRepository.save(SentLog
                                            .builder()
                                            .pledgeId(payload.getId())
                                            .status(200)
                                            .build());
                                    log.info("SentLog with pledgeId {} was saved ", payload.getId());
                                }
                            });

        } catch (JsonProcessingException e) {
            log.error("Error json processing id: {} was not sent", payload.getId(), e);
        } catch (Exception e) {
            log.error("message id: {} was not sent", payload.getId(), e);
        }
        return null;
    }
}
