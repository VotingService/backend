package com.example.votingService.repository.ballot;

import com.example.votingService.domain.ballot.Ballot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface BallotRepository extends JpaRepository<Ballot, Integer> {

    @Query(value = "select b.id from Ballot b where b.voter.id = :id")
    public List<Ballot> getAllBallotsByVoterId(Integer id);

//    boolean existsCurrentAccountByEmail(String email);
}
