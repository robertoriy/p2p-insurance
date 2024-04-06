package ru.nsu.insurance.p2p.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.insurance.p2p.dao.entity.InsuranceGroup;

public interface GroupRepository extends JpaRepository<InsuranceGroup, Long> {
}
