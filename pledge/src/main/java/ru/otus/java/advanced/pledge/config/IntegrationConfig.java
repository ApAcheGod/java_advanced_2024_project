package ru.otus.java.advanced.pledge.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import ru.otus.java.advanced.pledge.service.PledgeMonitoringSenderHandler;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {

    private final PledgeMonitoringSenderHandler pledgeMonitoringSenderHandler;

    @Bean
    public DirectChannel calculateChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow calculationFlow() {
        return IntegrationFlow.from(calculateChannel())
                .log(LoggingHandler.Level.INFO)
                .handle(pledgeMonitoringSenderHandler)
                .get();
    }
}
