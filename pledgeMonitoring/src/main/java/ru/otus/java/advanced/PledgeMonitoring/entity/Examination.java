package ru.otus.java.advanced.PledgeMonitoring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name = "EXAMINATION")
@EntityListeners(AuditingEntityListener.class)
public class Examination {

    @Id
    @Column(name = "ID")
    @UuidGenerator
    private UUID id;

    @Column(name="CONTRACT_ID")
    private UUID contractId;

    @Column(name="PLEDGE_ID")
    private UUID pledgeId;

    @Column(name="EXAMINATION_TYPE")
    private UUID examinationType;

    @Column(name = "EXAMINATION_STATUS")
    private UUID examinationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MONITORING_PATTERN_ID")
    private MonitoringPattern monitoringPattern;

    @Column(name = "PLAN_EXAMINATION_DATE")
    private LocalDate planExaminationDate;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

}