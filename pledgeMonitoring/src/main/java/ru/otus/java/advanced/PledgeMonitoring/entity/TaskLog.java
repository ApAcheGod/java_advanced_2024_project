package ru.otus.java.advanced.PledgeMonitoring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name = "TASK_LOG")
@EntityListeners(AuditingEntityListener.class)
public class TaskLog {

    @Id
    @Column(name = "ID")
    @UuidGenerator
    private UUID id;

    @Column(name = "CONTRACT_ID")
    private UUID contractId;

    @Column(name = "TASK_ID")
    private UUID taskId;

    @Column(name = "TASK_STATUS")
    private UUID taskStatus;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

}
