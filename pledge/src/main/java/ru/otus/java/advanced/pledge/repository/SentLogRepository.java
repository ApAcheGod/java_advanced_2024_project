package ru.otus.java.advanced.pledge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.java.advanced.pledge.entity.SentLog;

import java.util.UUID;

public interface SentLogRepository extends JpaRepository<SentLog, UUID> {
}
