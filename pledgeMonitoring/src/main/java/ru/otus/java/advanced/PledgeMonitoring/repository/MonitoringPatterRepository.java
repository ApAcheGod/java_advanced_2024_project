package ru.otus.java.advanced.PledgeMonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.java.advanced.PledgeMonitoring.entity.MonitoringPattern;

import java.util.Optional;
import java.util.UUID;

public interface MonitoringPatterRepository extends JpaRepository<MonitoringPattern, UUID> {

    Optional<MonitoringPattern> findByPattern(String pattern);
}
