package ru.otus.java.advanced.PledgeMonitoring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.java.advanced.PledgeMonitoring.dto.ContractDto;

import java.util.UUID;

@FeignClient("contract-service")
public interface ContractServiceClient {

    @GetMapping("/api/contract/{id}")
    ResponseEntity<ContractDto> getContract(@PathVariable("id") UUID id);
}
