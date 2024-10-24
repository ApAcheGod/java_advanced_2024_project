package ru.otus.java.advanced.client.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ClientDto {
    private UUID id;
    private String name;
    private String inn;
    private LocalDateTime createdAt;
}
