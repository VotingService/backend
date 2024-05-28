package com.example.votingService.service.votingstrategy;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.request.CreateBallotRequest;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.election.ElectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DistributionVotingStrategy implements VotingStrategy {
    @Autowired
    private final BallotRepository ballotRepository;
    @Autowired
    private final ElectionRepository electionRepository;

    @Override
    public void vote(Integer election_id, Integer voter_id, List<CreateBallotRequest> ballot_entries) throws IllegalArgumentException {
        int total_votes = 0;

        for (CreateBallotRequest ballot_entry: ballot_entries) {

            total_votes += ballot_entry.getCandidatePosition();

            if (total_votes > electionRepository.findById(ballot_entry.getElection_id()).get().getMaxVotes()) {
                throw new IllegalArgumentException("Exceeded max votes!");
            }
        }

        for (CreateBallotRequest ballot_entry: ballot_entries)
        {
            ballotRepository.saveBallotEntry(
                    election_id,
                    voter_id,
                    ballot_entry.getCandidate_id(),
                    ballot_entry.getCandidatePosition()
            );
        }

    }
}
