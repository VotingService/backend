package com.example.votingService.service.votingstrategy;


import com.example.votingService.domain.user.User;

import java.util.List;

public interface VotingStrategy {

    public void vote(Integer election_id, Integer voter_id, Integer candidate_id, Integer candidatePosition);
    public List<User> seeElectionWinner(Integer election_id);
}
