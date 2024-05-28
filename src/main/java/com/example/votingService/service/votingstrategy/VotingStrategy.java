package com.example.votingService.service.votingstrategy;


import com.example.votingService.domain.request.ballot.CreateBallotRequest;

import java.util.List;

public interface VotingStrategy {

    public void vote(Integer election_id, Integer voter_id, List<CreateBallotRequest> ballot_entries);
}
