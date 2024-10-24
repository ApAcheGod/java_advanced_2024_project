package ru.otus.java.advanced.rootApp.client.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CreateContractDto {
    private String name;
    private UUID clientId;
    private UUID contractType;
    private BigDecimal amount;
}
