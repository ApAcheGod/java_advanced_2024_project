package ru.otus.java.advanced.rootApp.client.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class PledgeDto {

    private UUID id;
    private String name;
    private UUID contractId;
    private BigDecimal pledgeCost;
    private BigDecimal contractCost;
    private BigDecimal estimatedCost;
    private UUID liquidityType;
    private String liquidityName;
    private UUID qualityType;
    private String qualityName;
    private UUID pledgeType;
    private String pledgeTypeName;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
