package ru.nsu.insurance.p2p.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.insurance.p2p.dao.entity.InsuranceTransaction;

public interface TransactionRepository extends JpaRepository<InsuranceTransaction, Long> {
}
