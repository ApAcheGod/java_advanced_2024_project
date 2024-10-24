package ru.otus.java.advanced.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.java.advanced.client.entity.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

}
