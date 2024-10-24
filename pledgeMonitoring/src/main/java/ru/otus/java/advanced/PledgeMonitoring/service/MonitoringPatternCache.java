package ru.otus.java.advanced.PledgeMonitoring.service;

import ru.otus.java.advanced.PledgeMonitoring.entity.MonitoringPattern;

import java.util.Optional;

public interface MonitoringPatternCache {

    Optional<MonitoringPattern> getMonitoringPattern(String pattern);

}
