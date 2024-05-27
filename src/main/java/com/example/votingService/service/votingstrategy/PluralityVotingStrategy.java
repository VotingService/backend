package com.example.votingService.service.votingstrategy;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.election.ElectionRepository;
import com.example.votingService.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PluralityVotingStrategy implements VotingStrategy {

    @Autowired
    private final BallotRepository ballotRepository;
    @Autowired
    private final ElectionRepository electionRepository;
    @Autowired
    private final UserRepository userRepository;
    @Override
    public void vote(Integer election_id, Integer voter_id, ArrayList<Ballot> ballot_entries) {

        for (Ballot ballot_entry: ballot_entries)
        {
            if (ballot_entry.getCandidatePosition() == 1)
            {
                ballotRepository.saveBallotEntry(
                        ballot_entry.getId(),
                        ballot_entry.getCreatedAt(),
                        ballot_entry.getUpdatedAt(),
                        ballot_entry.getElection().getId(),
                        ballot_entry.getVoter().getId(),
                        ballot_entry.getCandidate().getId(),
                        ballot_entry.getCandidatePosition()
                );

                break;
            }
        }

    }
}
