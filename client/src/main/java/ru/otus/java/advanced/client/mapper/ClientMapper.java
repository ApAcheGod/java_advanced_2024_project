package ru.otus.java.advanced.client.mapper;

import org.springframework.stereotype.Component;
import ru.otus.java.advanced.client.dto.ClientDto;
import ru.otus.java.advanced.client.dto.CreateClientDto;
import ru.otus.java.advanced.client.entity.Client;

@Component
public class ClientMapper {

    public ClientDto toDto(Client client) {
        return ClientDto
                .builder()
                .id(client.getId())
                .name(client.getName())
                .inn(client.getInn())
                .createdAt(client.getCreatedAt())
                .build();
    }

    public Client toEntity(CreateClientDto createClientDto) {
        return Client
                .builder()
                .deleted(false)
                .name(createClientDto.getName())
                .inn(createClientDto.getInn())
                .build();
    }
}
