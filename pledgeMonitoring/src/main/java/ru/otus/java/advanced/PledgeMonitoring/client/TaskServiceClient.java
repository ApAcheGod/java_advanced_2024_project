package ru.otus.java.advanced.PledgeMonitoring.client;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.java.advanced.PledgeMonitoring.dto.CreateTaskDto;
import ru.otus.java.advanced.PledgeMonitoring.dto.TaskDto;

@FeignClient("task-service")
public interface TaskServiceClient {

    @PostMapping("/api/task")
    @Retry(name = "default")
    ResponseEntity<TaskDto> addTask(CreateTaskDto createTaskDto);

}
