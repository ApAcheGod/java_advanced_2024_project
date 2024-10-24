package ru.otus.java.advanced.PledgeMonitoring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.java.advanced.PledgeMonitoring.entity.MonitoringPattern;
import ru.otus.java.advanced.PledgeMonitoring.repository.MonitoringPatterRepository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MonitoringPatternCacheImpl implements MonitoringPatternCache {

    private final MonitoringPatterRepository monitoringPatterRepository;
    private final ConcurrentHashMap<String, MonitoringPattern> cache = new ConcurrentHashMap<>();

    @Override
    public Optional<MonitoringPattern> getMonitoringPattern(String pattern) {
        if (!cache.containsKey(pattern)) {
            Optional<MonitoringPattern> monitoringPatternOptional = monitoringPatterRepository.findByPattern(pattern);
            monitoringPatternOptional.ifPresent(monitoringPattern -> cache.put(pattern, monitoringPattern));
        }
        return Optional.ofNullable(cache.get(pattern));
    }
}
