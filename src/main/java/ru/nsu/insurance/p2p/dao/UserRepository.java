package ru.nsu.insurance.p2p.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.insurance.p2p.dao.entity.UserProfile;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {
}

