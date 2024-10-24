package ru.otus.java.advanced.contract.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.java.advanced.contract.client.DictionaryClient;
import ru.otus.java.advanced.contract.dto.ContractDto;
import ru.otus.java.advanced.contract.dto.CreateContractDto;
import ru.otus.java.advanced.contract.entity.Contract;

@Component
@RequiredArgsConstructor
public class ContractMapper {

    private final DictionaryClient dictionaryClient;

    public Contract toEntity(CreateContractDto createContractDto) {
        return Contract
                .builder()
                .name(createContractDto.getName())
                .clientId(createContractDto.getClientId())
                .contractType(createContractDto.getContractType())
                .amount(createContractDto.getAmount())
                .build();
    }

    public ContractDto toDto(Contract contract) {
        return ContractDto
                .builder()
                .id(contract.getId())
                .name(contract.getName())
                .contractTypeId(contract.getContractType())
                .contractTypeName(dictionaryClient.getDictionaryById(contract.getContractType()).getName())
                .clientId(contract.getClientId())
                .amount(contract.getAmount())
                .createdAt(contract.getCreatedAt())
                .build();
    }
}
