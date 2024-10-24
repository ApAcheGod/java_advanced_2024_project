package ru.otus.java.advanced.pledge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.pledge.dto.CreatePledgeRequest;
import ru.otus.java.advanced.pledge.dto.PledgeDto;
import ru.otus.java.advanced.pledge.entity.Pledge;
import ru.otus.java.advanced.pledge.mapper.PledgeMapper;
import ru.otus.java.advanced.pledge.repository.PledgeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PledgeServiceImpl implements PledgeService {

    private final PledgeRepository pledgeRepository;
    private final PledgeMapper pledgeMapper;

    @Override
    public Pledge add(CreatePledgeRequest createPledgeRequest) {
        Pledge pledge = pledgeMapper.toEntity(createPledgeRequest);
        return pledgeRepository.save(pledge);
    }

    @Override
    public Optional<Pledge> findById(UUID id) {
        return pledgeRepository.findById(id);
    }

    @Override
    public List<Pledge> findAll() {
        return pledgeRepository.findAll();
    }

    @Override
    public Pledge update(PledgeDto pledgeDto) {
        Optional<Pledge> optionalPledge = pledgeRepository.findById(pledgeDto.getId());
        if (optionalPledge.isPresent()) {
            Pledge pledge = optionalPledge.get();
            pledge.setName(pledgeDto.getName());
            pledge.setContractCost(pledgeDto.getContractCost());
            pledge.setContractId(pledgeDto.getContractId());
            pledge.setPledgeCost(pledgeDto.getPledgeCost());
            pledge.setLiquidityType(pledgeDto.getLiquidityType());
            pledge.setQualityType(pledgeDto.getQualityType());
            pledge.setEstimatedCost(pledgeDto.getEstimatedCost());
            return pledgeRepository.save(pledge);
        }
        throw new EntityNotFoundException("Pledge with id " + pledgeDto.getId() + " not found");
    }


}
