package ru.otus.java.advanced.contract.service;

import ru.otus.java.advanced.contract.dto.ContractDto;
import ru.otus.java.advanced.contract.dto.CreateContractDto;
import ru.otus.java.advanced.contract.entity.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractService {

    Contract add(CreateContractDto createContractDto);

    Contract update(ContractDto contractDto);

    Optional<Contract> findById(UUID id);

    List<Contract> findAll();
}
