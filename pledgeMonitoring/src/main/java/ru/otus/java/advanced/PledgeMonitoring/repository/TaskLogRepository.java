package ru.otus.java.advanced.PledgeMonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.java.advanced.PledgeMonitoring.entity.TaskLog;

import java.util.UUID;

public interface TaskLogRepository extends JpaRepository<TaskLog, UUID> {
}
