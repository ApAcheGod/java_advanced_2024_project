package ru.otus.java.advanced.PledgeMonitoring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name = "MONITORING_PATTERN")
public class MonitoringPattern {

    @Id
    @Column(name = "ID")
    @UuidGenerator
    private UUID id;

    @Column(name="PATTERN")
    private String pattern;

    @Column(name="PRICE_FIRST_MONITORING_PERIOD")
    private Long priceFirstMonitoringPeriod;

    @Column(name="PRICE_BASE_MONITORING_PERIOD")
    private Long priceBaseMonitoringPeriod;

    @Column(name="DOCUMENT_FIRST_MONITORING_PERIOD")
    private Long documentFirstMonitoringPeriod;

    @Column(name="DOCUMENT_BASE_MONITORING_PERIOD")
    private Long documentBaseMonitoringPeriod;

}