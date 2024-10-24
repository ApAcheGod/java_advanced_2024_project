package ru.otus.java.advanced.contract.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.contract.dto.ContractDto;
import ru.otus.java.advanced.contract.dto.CreateContractDto;
import ru.otus.java.advanced.contract.entity.Contract;
import ru.otus.java.advanced.contract.mapper.ContractMapper;
import ru.otus.java.advanced.contract.repository.ContractRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    @Override
    public Contract add(CreateContractDto createContractDto) {
        return contractRepository.save(contractMapper.toEntity(createContractDto));
    }

    @Override
    public Contract update(ContractDto contractDto) {
        Optional<Contract> optionalContract = contractRepository.findById(contractDto.getId());
        if (optionalContract.isPresent()) {
            Contract contract = optionalContract.get();
            contract.setName(contractDto.getName());
            contract.setContractType(contractDto.getContractTypeId());
            contract.setAmount(contractDto.getAmount());
            contract.setClientId(contractDto.getClientId());
            return contractRepository.save(contract);
        }
        throw new EntityNotFoundException("Contract with id " + contractDto.getId() + " not found");
    }

    @Override
    public Optional<Contract> findById(UUID id) {
        return contractRepository.findById(id);
    }

    @Override
    public List<Contract> findAll() {
        return contractRepository.findAll();
    }
}
