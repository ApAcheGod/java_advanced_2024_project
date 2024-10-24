package ru.otus.java.advanced.rootApp.client;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.java.advanced.rootApp.client.dto.ClientDto;
import ru.otus.java.advanced.rootApp.client.dto.CreateClientDto;

@FeignClient("client-service")
public interface ClientServiceClient {

    @PostMapping("/api/client")
    @Retry(name = "default")
    ResponseEntity<ClientDto> addClient(CreateClientDto createClientDto);
}
