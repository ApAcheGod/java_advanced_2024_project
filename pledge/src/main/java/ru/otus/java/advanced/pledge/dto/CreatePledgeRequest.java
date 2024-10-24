package ru.otus.java.advanced.pledge.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreatePledgeRequest {
    private String name;
    private UUID contractId;
    private BigDecimal pledgeCost;
    private BigDecimal contractCost;
    private BigDecimal estimatedCost;
    private UUID liquidityType;
    private UUID qualityType;
    private UUID pledgeType;
}
