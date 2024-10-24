package ru.otus.java.advanced.rootApp.client.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClientDto {
    private String name;
    private String inn;
}
