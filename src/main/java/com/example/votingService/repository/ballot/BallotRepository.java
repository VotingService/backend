package com.example.votingService.repository.ballot;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.user.User;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface BallotRepository extends JpaRepository<Ballot, Integer> {

    @Query(value = "select b from Ballot b where b.voter.id = :id")
    public List<Ballot> getAllBallotsByVoterId(Integer id);

    @Query(value = "select b from Ballot b where b.voter.id = :voter_id and b.election.id = :election_id")
    public List<Ballot> getAllBallotsOfVoterInElection(Integer voter_id, Integer election_id);

    @Query(value = "select b from Ballot b where b.voter.id = :voter_id and b.election.id = :election_id and b.candidate.id = :candidate_id")
    public Ballot getBallotByVoterCandidateAndElection(Integer voter_id, Integer election_id, Integer candidate_id);

    @Query("SELECT e FROM Election e JOIN Ballot b on e = b.election WHERE b.voter.id = :id")
    public List<Election> getAllElectionByVoterId(Integer id);

    // correct this query
    @Query(value = "INSERT INTO Ballot(id, " +
            "created_at, " +
            "updated_at, " +
            "election_id, " +
            "user_id, " +
            "candidate_id, " +
            "candidate_position) VALUES(:id, :createdAt, :updatedAt, :electionId, :userId, :candidateId, :candidatePosition)", nativeQuery = true)
    public void saveBallotEntry(@Param("id") Integer id,
                                @Param("createdAt") Timestamp createdAt,
                                @Param("updatedAt") Timestamp updatedAt,
                                @Param("electionId") Integer electionId,
                                @Param("userId") Integer userId,
                                @Param("candidateId") Integer candidateId,
                                @Param("candidatePosition") Integer candidatePosition
    );

    @Query(value = "select b from Ballot b where b.election.id = :id")
    public List<Ballot> getAllBallotsByElectionId(Integer id);
}
