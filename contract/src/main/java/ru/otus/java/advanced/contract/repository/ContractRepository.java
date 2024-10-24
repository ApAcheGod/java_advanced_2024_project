package ru.otus.java.advanced.contract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.java.advanced.contract.entity.Contract;

import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {
}
