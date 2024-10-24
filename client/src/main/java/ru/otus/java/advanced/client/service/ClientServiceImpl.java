package ru.otus.java.advanced.client.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.java.advanced.client.dto.ClientDto;
import ru.otus.java.advanced.client.dto.CreateClientDto;
import ru.otus.java.advanced.client.entity.Client;
import ru.otus.java.advanced.client.mapper.ClientMapper;
import ru.otus.java.advanced.client.repository.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public Client add(CreateClientDto createClientDto) {
        log.info("add client: {}", createClientDto);
        return clientRepository.save(clientMapper.toEntity(createClientDto));
    }

    @Override
    public Client update(ClientDto clientDto) {
        Optional<Client> clientOptional = clientRepository.findById(clientDto.getId());
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setName(clientDto.getName());
            client.setInn(clientDto.getInn());
            return clientRepository.save(client);
        }
        throw new EntityNotFoundException("Client with id " + clientDto.getId() + " not found");
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return clientRepository.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}
