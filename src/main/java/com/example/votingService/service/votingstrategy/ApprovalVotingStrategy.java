package com.example.votingService.service.votingstrategy;

import com.example.votingService.domain.ballot.Ballot;
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
    @Autowired
    private final ElectionRepository electionRepository;
    @Autowired
    private final UserRepository userRepository;
    @Override
    public void vote(Integer election_id, Integer voter_id, ArrayList<Ballot> ballot_entries) {
        List<Ballot> ballots = ballotRepository.getAllBallotsOfVoterInElection(voter_id, election_id);

        for (Ballot ballot_entry: ballot_entries)
        {
            ballotRepository.saveBallotEntry(ballot_entry);
        }
    }
}
