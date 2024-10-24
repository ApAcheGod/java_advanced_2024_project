package ru.otus.java.advanced.pledge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.advanced.pledge.dto.CreatePledgeRequest;
import ru.otus.java.advanced.pledge.dto.PledgeDto;
import ru.otus.java.advanced.pledge.mapper.PledgeMapper;
import ru.otus.java.advanced.pledge.service.PledgeService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PledgeController {

    private final PledgeService pledgeService;
    private final PledgeMapper pledgeMapper;

    @Operation(summary = "Получение объекта залога")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение объекта залога",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PledgeDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Объекта залога не найден",
                    content = @Content) })
    @GetMapping("/api/pledge/{id}")
    public ResponseEntity<PledgeDto> getPledge(@PathVariable("id") UUID id) {
        log.info("Запрос на получение объекта залога {}", id);
        return pledgeService.findById(id)
                .map(pledge -> ResponseEntity.ok(pledgeMapper.toDto(pledge)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получение списка объектов залога")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение объекта залога",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PledgeDto.class)) })
    })
    @GetMapping("/api/pledge")
    public ResponseEntity<List<PledgeDto>> getPledge() {
        log.info("Запрос на получение списка объекта залога");
        return ResponseEntity.ok(pledgeService.findAll()
                .stream()
                .map(pledgeMapper::toDto)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Создание объекта залога")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание объекта залога",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PledgeDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Ошибка",
                    content = @Content) })
    @PostMapping("/api/pledge")
    public ResponseEntity<PledgeDto> addPledge(@RequestBody CreatePledgeRequest createPledgeRequest) {
        log.info("Запрос на создание объекта залога {}", createPledgeRequest.getName());
        return ResponseEntity.ok(pledgeMapper.toDto(pledgeService.add(createPledgeRequest)));
    }

    @Operation(summary = "Обновление объекта залога")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление объекта залога",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PledgeDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Ошибка",
                    content = @Content) })
    @PutMapping("/api/pledge")
    public ResponseEntity<PledgeDto> updatePledge(@RequestBody PledgeDto pledgeDto) {
        log.info("Запрос на обновление договора: {}", pledgeDto.getName());
        return ResponseEntity.ok(pledgeMapper.toDto(pledgeService.update(pledgeDto)));
    }
}
