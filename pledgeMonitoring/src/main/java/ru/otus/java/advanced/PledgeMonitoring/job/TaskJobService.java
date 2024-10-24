package ru.otus.java.advanced.PledgeMonitoring.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.PledgeMonitoring.client.TaskServiceClient;
import ru.otus.java.advanced.PledgeMonitoring.dto.CreateTaskDto;
import ru.otus.java.advanced.PledgeMonitoring.entity.TaskLog;
import ru.otus.java.advanced.PledgeMonitoring.repository.ExaminationRepository;
import ru.otus.java.advanced.PledgeMonitoring.repository.TaskLogRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskJobService {

    private final ExaminationRepository examinationRepository;
    private final TaskLogRepository taskLogRepository;
    private final TaskServiceClient taskServiceClient;

    @Scheduled(cron = "${task.create.rate: 0 0/1 * * * *}")
    public void createTask() {
        log.info("Поиск договоров для создания задач");
        List<UUID> contractIds = examinationRepository.findAllContractForCreateTask();
        log.info("Найдено договоров: {}", contractIds.size());
        contractIds
                .stream()
                .peek( contractId -> log.info("Отправка договора {} для создания задачи", contractId))
                .map(contractId -> taskServiceClient.addTask(
                        CreateTaskDto
                                .builder()
                                .contractId(contractId)
                                .build())
                )
                .map(ResponseEntity::getBody)
                .map(taskDto -> TaskLog
                        .builder()
                        .taskId(taskDto.getId())
                        .taskStatus(taskDto.getTaskStatus())
                        .contractId(taskDto.getContractId())
                        .build()
                )
                .forEach(taskLogRepository::save);


    }
}
