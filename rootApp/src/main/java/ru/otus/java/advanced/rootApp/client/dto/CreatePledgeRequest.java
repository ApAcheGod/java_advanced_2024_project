package ru.otus.java.advanced.rootApp.client.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
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
