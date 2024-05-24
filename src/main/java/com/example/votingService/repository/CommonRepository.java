package com.example.votingService.repository;

import com.example.votingService.domain.ballot.Ballot;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonRepository {

    public List<Ballot> getAllBallotsByElection();
}
