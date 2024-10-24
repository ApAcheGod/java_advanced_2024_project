package ru.otus.java.advanced.rootApp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.java.advanced.rootApp.client.dto.CreatePledgeRequest;
import ru.otus.java.advanced.rootApp.client.dto.PledgeDto;

@FeignClient("pledge-service")
public interface PledgeServiceClient {

    @PostMapping("/api/pledge")
    ResponseEntity<PledgeDto> addPledge(CreatePledgeRequest createPledgeRequest);
}
