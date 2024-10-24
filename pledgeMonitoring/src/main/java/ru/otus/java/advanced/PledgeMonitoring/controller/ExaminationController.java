package ru.otus.java.advanced.PledgeMonitoring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.advanced.PledgeMonitoring.dto.ExaminationDto;
import ru.otus.java.advanced.PledgeMonitoring.dto.ExaminationUpdateStatusDto;
import ru.otus.java.advanced.PledgeMonitoring.mapper.ExaminationMapper;
import ru.otus.java.advanced.PledgeMonitoring.service.ExaminationService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExaminationController {

    private final ExaminationService examinationService;
    private final ExaminationMapper examinationMapper;

    @Operation(summary = "Получение списка проверок по договору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка проверок",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExaminationDto.class))
            })})
    @GetMapping("/api/examinations/{contractId}")
    public ResponseEntity<List<ExaminationDto>> getExaminationsByContractId(@PathVariable("contractId") UUID contractId) {
        log.info("Запрос на получение списка проверок по договору {}", contractId);
        return ResponseEntity.ok(examinationService.findByContractId(contractId)
                .stream()
                .map(examinationMapper::toDto)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Обновление статуса проверки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление статуса проверки",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExaminationDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Проверка не найдена",
                    content = @Content) })
    @PatchMapping("/api/examinations")
    public ResponseEntity<ExaminationDto> getExaminationsByContractId(@RequestBody ExaminationUpdateStatusDto examinationUpdateStatusDto) {
        log.info("Запрос на обновление статуса проверки {}", examinationUpdateStatusDto.getExaminationId());
        return examinationService.updateStatus(examinationUpdateStatusDto)
                .map(examination -> ResponseEntity.ok(examinationMapper.toDto(examination)))
                .orElse(ResponseEntity.notFound().build());
    }

}
