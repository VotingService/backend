package com.example.votingService.service.votingstrategy;


import com.example.votingService.domain.request.ballot.BallotRequest;

import java.util.List;

public interface VotingStrategy {

    public void vote(Integer election_id, Integer voter_id, List<BallotRequest> ballot_entries) throws IllegalArgumentException;
}
