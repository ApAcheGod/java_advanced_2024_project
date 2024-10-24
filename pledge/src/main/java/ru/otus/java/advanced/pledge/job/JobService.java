package ru.otus.java.advanced.pledge.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.pledge.mapper.PledgeMapper;
import ru.otus.java.advanced.pledge.repository.PledgeRepository;
import ru.otus.java.advanced.pledge.service.PledgeService;


@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final PledgeRepository pledgeRepository;
    private final PledgeMapper pledgeMapper;
    private final DirectChannel calculateChannel;

    @Scheduled(fixedRate = 5000)
    public void sendForCalc() {
        log.info("sendForCalc start");
        pledgeRepository.findAllUnSent()
                .stream()
                .map(pledgeMapper::toDto)
                .map(GenericMessage::new)
                .forEach(calculateChannel::send);

        pledgeRepository.findAllUpdated()
                .stream()
                .map(pledgeMapper::toDto)
                .map(GenericMessage::new)
                .forEach(calculateChannel::send);
        log.info("sendForCalc finish");
    }
}
