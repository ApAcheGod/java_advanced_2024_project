package ru.otus.java.advanced.pledge.service;

import ru.otus.java.advanced.pledge.dto.CreatePledgeRequest;
import ru.otus.java.advanced.pledge.dto.PledgeDto;
import ru.otus.java.advanced.pledge.entity.Pledge;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PledgeService {
    Pledge add(CreatePledgeRequest createPledgeRequest);

    Optional<Pledge> findById(UUID id);

    List<Pledge> findAll();

    Pledge update(PledgeDto pledgeDto);
}
