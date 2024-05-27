package com.example.votingService.service.votingstrategy;


import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.request.CreateBallotRequest;

import java.util.ArrayList;
import java.util.List;

public interface VotingStrategy {

    public void vote(Integer election_id, Integer voter_id, List<CreateBallotRequest> ballot_entries);
}
