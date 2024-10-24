package ru.otus.java.advanced.rootApp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.advanced.proto.DictionaryByCategoryBCodeResponse;
import ru.otus.advanced.proto.DictionaryResponse;
import ru.otus.java.advanced.rootApp.client.ClientServiceClient;
import ru.otus.java.advanced.rootApp.client.ContractServiceClient;
import ru.otus.java.advanced.rootApp.client.DictionaryClient;
import ru.otus.java.advanced.rootApp.client.PledgeServiceClient;
import ru.otus.java.advanced.rootApp.client.dto.*;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RootService {

    private final ClientServiceClient clientServiceClient;
    private final ContractServiceClient contractServiceClient;
    private final PledgeServiceClient pledgeServiceClient;
    private final DictionaryClient dictionaryClient;

    @Scheduled(cron = "${cron.emulate: 0 0/1 * * * *}")
    public void emulate() {
        ResponseEntity<ClientDto> client = clientServiceClient.addClient(
                CreateClientDto
                        .builder()
                        .inn("client inn")
                        .name("client name")
                        .build());
        log.info("client: {}", client);

        ResponseEntity<ContractDto> contract = contractServiceClient.createContract(
                CreateContractDto
                        .builder()
                        .clientId(client.getBody().getId())
                        .contractType(UUID.fromString(dictionaryClient.getDictionaryByCode("Contract_Type_Pledge").getId()))
                        .amount(BigDecimal.TEN)
                        .name("pledge name")
                        .build());
        log.info("contract: {}", contract);

        DictionaryByCategoryBCodeResponse guaranteeKinds = dictionaryClient.getDictionaryByCategoryBCode("Guarantee_Kind");
        DictionaryByCategoryBCodeResponse liquidityTypes = dictionaryClient.getDictionaryByCategoryBCode("Liquidity_Type");
        DictionaryByCategoryBCodeResponse qualityTypes = dictionaryClient.getDictionaryByCategoryBCode("Quality_Type");

        int counter = 0;
        log.info("Создание объектов залога");
        for (DictionaryResponse guaranteeKind : guaranteeKinds.getDictionaryResponseList()) {
            for (DictionaryResponse liquidityType : liquidityTypes.getDictionaryResponseList()) {
                for (DictionaryResponse qualityType : qualityTypes.getDictionaryResponseList()) {
                    ResponseEntity<PledgeDto> pledge = pledgeServiceClient.addPledge(
                            CreatePledgeRequest
                                    .builder()
                                    .contractId(contract.getBody().getId())
                                    .contractCost(BigDecimal.TEN)
                                    .estimatedCost(BigDecimal.TEN)
                                    .pledgeCost(BigDecimal.TEN)
                                    .pledgeType(UUID.fromString(guaranteeKind.getId()))
                                    .liquidityType(UUID.fromString(liquidityType.getId()))
                                    .qualityType(UUID.fromString(qualityType.getId()))
                                    .name("Объект залога №" + (counter++))
                                    .build());
                    log.info("pledge: {}", pledge);
                }
            }
        }


    }
}
