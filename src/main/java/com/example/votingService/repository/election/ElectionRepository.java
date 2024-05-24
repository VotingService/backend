package com.example.votingService.repository.election;

import com.example.votingService.domain.election.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Integer> {

    public List<Integer> getAllCandidatesByElection();
}
