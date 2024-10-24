package ru.otus.java.advanced.contract.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.advanced.contract.dto.ContractDto;
import ru.otus.java.advanced.contract.dto.CreateContractDto;
import ru.otus.java.advanced.contract.mapper.ContractMapper;
import ru.otus.java.advanced.contract.service.ContractService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final ContractMapper contractMapper;

    @Operation(summary = "Получение договора")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение договора",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContractDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Договор не найден",
                    content = @Content) })
    @GetMapping("/api/contract/{id}")
    public ResponseEntity<ContractDto> getContract(@PathVariable("id") UUID id) {
        log.info("Запрос на получение договора {}", id);
        return contractService.findById(id)
                .map(contract -> ResponseEntity.ok(contractMapper.toDto(contract)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получение списка договоров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка договоров",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContractDto.class)) })
    })
    @GetMapping("/api/contract")
    public ResponseEntity<List<ContractDto>> getContracts() {
        log.info("Запрос на получение списка договоров");
        return ResponseEntity.ok(contractService.findAll()
                .stream()
                .map(contractMapper::toDto)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Создание договора")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание договора",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContractDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Ошибка",
                    content = @Content) })
    @PostMapping("/api/contract")
    public ResponseEntity<ContractDto> createContract(@RequestBody CreateContractDto createContractDto) {
        log.info("Запрос на создание договора: {}", createContractDto.getName());
        return ResponseEntity.ok(contractMapper.toDto(contractService.add(createContractDto)));
    }


    @Operation(summary = "Обновление договора")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление договора",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContractDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Ошибка",
                    content = @Content) })
    @PutMapping("/api/contract")
    public ResponseEntity<ContractDto> updateContract(@RequestBody ContractDto contractDto) {
        log.info("Запрос на обновление договора: {}", contractDto.getName());
        return ResponseEntity.ok(contractMapper.toDto(contractService.update(contractDto)));
    }
}
