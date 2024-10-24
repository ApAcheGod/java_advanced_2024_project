package ru.otus.java.advanced.contract.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ContractDto {
    private UUID id;
    private String name;
    private UUID clientId;
    private UUID contractTypeId;
    private String contractTypeName;
    private BigDecimal amount;
    private LocalDateTime createdAt;

}
