package ru.otus.java.advanced.rootApp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.java.advanced.rootApp.client.dto.ContractDto;
import ru.otus.java.advanced.rootApp.client.dto.CreateContractDto;

@FeignClient("contract-service")
public interface ContractServiceClient {

    @PostMapping("/api/contract")
    ResponseEntity<ContractDto> createContract(CreateContractDto createContractDto);
}
