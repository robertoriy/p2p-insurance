package ru.nsu.insurance.p2p.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.insurance.p2p.dao.entity.InsuranceContract;

public interface ContractRepository extends JpaRepository<InsuranceContract, Long> {
}
