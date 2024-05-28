package com.example.votingService.repository.election;

import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Integer> {

    @Query("SELECT c FROM Election e JOIN e.candidates c WHERE e.id = :id")
    public List<User> getAllCandidatesByElectionId(Integer id);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM candidate_election WHERE election_id = :electionId AND candidate_id = :candidateId", nativeQuery = true)
    void removeCandidateFromElection(@Param("electionId") Integer electionId, @Param("candidateId") Integer candidateId);
    @Query(value = "INSERT INTO candidate_election(candidate_id, election_id) VALUES (:candidateId, :electionId)", nativeQuery = true)
    void addCandidateToElection(@Param("electionId") Integer electionId, @Param("candidateId") Integer candidateId);
}
