package com.example.votingService.repository.election;

import com.example.votingService.domain.election.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Integer> {

//    @Query
//    public List<Integer> getAllCandidatesByElection(Election election);
}