package com.example.votingService.repository.ballot;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.user.User;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

//    @Query(value = "select b from Ballot b where b.voter.id = :voter_id and b.election.id = :election_id and b.candidate.id = :candidate_id")
//    public Ballot getBallotByVoterCandidateAndElection(Integer voter_id, Integer election_id, Integer candidate_id);

    @Query("SELECT e FROM Election e JOIN Ballot b on e = b.election WHERE b.voter.id = :id")
    public List<Election> getAllElectionByVoterId(Integer id);

    @Query("SELECT SUM(b.candidatePoint) FROM Ballot b WHERE b.candidate.id = :id")
    public Integer getPointsOfCandidate(Integer id);

    // correct this query
    @Query(value = "INSERT INTO ballots(election_id, " +
            "user_id, " +
            "candidate_id, " +
            "candidate_point) VALUES(:electionId, :voterId, :candidateId, :candidatePoint)", nativeQuery = true)
    public void saveBallotEntry(@Param("electionId") Integer electionId,
                                @Param("voterId") Integer voterId,
                                @Param("candidateId") Integer candidateId,
                                @Param("candidatePoint") Integer candidatePoint
    );

    @Query(value = "select b from Ballot b where b.election.id = :id")
    public List<Ballot> getAllBallotsByElectionId(Integer id);

    @Modifying
    @Query("UPDATE Ballot b SET b.election.id = :electionId, b.voter.id = :voterId, b.candidate.id = :candidateId, b.candidatePoint = :candidatePoint WHERE b.id = :id")
    public void update(@Param("id") Integer id,
                       @Param("electionId") Integer electionId,
                       @Param("voterId") Integer voterId,
                       @Param("candidateId") Integer candidateId,
                       @Param("candidatePoint") Integer candidatePoint);
}
