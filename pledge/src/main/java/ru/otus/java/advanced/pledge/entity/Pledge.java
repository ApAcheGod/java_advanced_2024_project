package ru.otus.java.advanced.pledge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name = "PLEDGE")
@EntityListeners(AuditingEntityListener.class)
public class Pledge {

    @Id
    @Column(name = "ID")
    @UuidGenerator
    private UUID id;

    @Column(name="NAME")
    private String name;

    @Column(name="CONTRACT_ID")
    private UUID contractId;

    @Column(name="PLEDGE_COST")
    private BigDecimal pledgeCost;

    @Column(name="CONTRACT_COST")
    private BigDecimal contractCost;

    @Column(name="ESTIMATED_COST")
    private BigDecimal estimatedCost;

    @Column(name="LIQUIDITY_TYPE")
    private UUID liquidityType;

    @Column(name="PLEDGE_TYPE")
    private UUID pledgeType;

    @Column(name="QUALITY_TYPE")
    private UUID qualityType;

    @Column(name="DELETED")
    private Boolean deleted;

    @CreationTimestamp
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="UPDATED_AT")
    private LocalDateTime updatedAt;

}
