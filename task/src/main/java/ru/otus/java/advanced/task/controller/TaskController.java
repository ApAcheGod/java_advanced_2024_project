package ru.otus.java.advanced.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.advanced.task.dto.ChangeStatusDto;
import ru.otus.java.advanced.task.dto.CreateTaskDto;
import ru.otus.java.advanced.task.dto.TaskDto;
import ru.otus.java.advanced.task.mapper.TaskMapper;
import ru.otus.java.advanced.task.service.TaskService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Operation(summary = "Получение списка задач")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка задач",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))})
    })
    @GetMapping("/api/task")
    public ResponseEntity<List<TaskDto>> getTasks() {
        log.info("Запрос на получение списка задач");
        return ResponseEntity.ok(taskService.findAll()
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Получение списка задач по договору ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка задач по договору ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))})
    })
    @GetMapping("/api/task/{contractId}")
    public ResponseEntity<List<TaskDto>> getTasks(@PathVariable("contractId") UUID contractId) {
        log.info("Запрос на получение списка задач по договору {}", contractId);
        return ResponseEntity.ok(taskService.findAllByContractId(contractId)
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Создание задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное Создание задачи",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Ошибка",
                    content = @Content) })
    @PostMapping("/api/task")
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskDto createTaskDto) {
        log.info("Запрос на создание задачи {}", createTaskDto.getContractId());
        return ResponseEntity.ok(taskMapper.toDto(taskService.createTask(createTaskDto.getContractId())));
    }

    @Operation(summary = "Обновление задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление задачи",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Задача не найдена",
                    content = @Content) })
    @PatchMapping("/api/task")
    public ResponseEntity<TaskDto> updateTask(@RequestBody ChangeStatusDto changeStatusDto) {
        log.info("Запрос на обновление задачи {}", changeStatusDto.getId());
        return taskService.changeStatus(changeStatusDto)
                .map(task -> ResponseEntity.ok(taskMapper.toDto(task)))
                .orElse(ResponseEntity.notFound().build());
    }

}
