package com.example.votingService.service.votingstrategy;


public interface VotingStrategy {

    public void vote(Integer election_id, Integer voter_id, Integer candidate_id, Integer candidatePosition);
}
