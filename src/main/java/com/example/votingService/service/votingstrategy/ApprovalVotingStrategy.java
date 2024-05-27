package com.example.votingService.service.votingstrategy;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.request.CreateBallotRequest;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.election.ElectionRepository;
import com.example.votingService.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApprovalVotingStrategy implements VotingStrategy{

    @Autowired
    private final BallotRepository ballotRepository;
    @Override
    public void vote(Integer election_id, Integer voter_id, List<CreateBallotRequest> ballot_entries) {
        List<Ballot> ballots = ballotRepository.getAllBallotsOfVoterInElection(voter_id, election_id);

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
