package ru.otus.java.advanced.pledge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
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
@Table(name = "SENT_LOG")
@EntityListeners(AuditingEntityListener.class)
public class SentLog {

    @Id
    @Column(name = "ID")
    @UuidGenerator
    private UUID id;

    @Column(name = "PLEDGE_ID")
    private UUID pledgeId;

    @Column(name = "STATUS")
    private Integer status;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;


}
