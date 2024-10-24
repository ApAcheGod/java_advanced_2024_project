package ru.otus.java.advanced.pledge.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.java.advanced.pledge.client.DictionaryClient;
import ru.otus.java.advanced.pledge.dto.CreatePledgeRequest;
import ru.otus.java.advanced.pledge.dto.PledgeDto;
import ru.otus.java.advanced.pledge.entity.Pledge;

@Component
@RequiredArgsConstructor
public class PledgeMapper {

    private final DictionaryClient dictionaryClient;

    public Pledge toEntity(CreatePledgeRequest createPledgeRequest) {
        return Pledge
                .builder()
                .name(createPledgeRequest.getName())
                .contractCost(createPledgeRequest.getContractCost())
                .contractId(createPledgeRequest.getContractId())
                .pledgeCost(createPledgeRequest.getPledgeCost())
                .pledgeType(createPledgeRequest.getPledgeType())
                .liquidityType(createPledgeRequest.getLiquidityType())
                .qualityType(createPledgeRequest.getQualityType())
                .estimatedCost(createPledgeRequest.getEstimatedCost())
                .deleted(false)
                .build();
    }

    public PledgeDto toDto(Pledge pledge) {
        return PledgeDto
                .builder()
                .id(pledge.getId())
                .name(pledge.getName())
                .contractCost(pledge.getContractCost())
                .contractId(pledge.getContractId())
                .pledgeCost(pledge.getPledgeCost())
                .liquidityType(pledge.getLiquidityType())
                .pledgeType(pledge.getQualityType())
                .qualityType(pledge.getQualityType())
                .estimatedCost(pledge.getEstimatedCost())
                .createdAt(pledge.getCreatedAt())
                .updatedAt(pledge.getUpdatedAt())
                .deleted(pledge.getDeleted())
                .pledgeTypeName(dictionaryClient.getDictionaryById(pledge.getPledgeType()).getName())
                .qualityName(dictionaryClient.getDictionaryById(pledge.getQualityType()).getName())
                .liquidityName(dictionaryClient.getDictionaryById(pledge.getLiquidityType()).getName())
                .build();
    }
}
