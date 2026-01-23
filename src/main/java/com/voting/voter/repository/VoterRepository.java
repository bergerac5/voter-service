package com.voting.voter.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.voting.voter.models.Voter;

@Repository
public interface VoterRepository extends JpaRepository<Voter, UUID> {
    boolean existsByUserId(UUID userId);

    boolean existsByNationalId(String nationalId);

    Voter findByUserId(UUID userId);
}
