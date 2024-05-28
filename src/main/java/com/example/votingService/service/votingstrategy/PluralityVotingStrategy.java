package com.example.votingService.service.votingstrategy;

import com.example.votingService.domain.request.ballot.CreateBallotRequest;
import com.example.votingService.repository.ballot.BallotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PluralityVotingStrategy implements VotingStrategy {

    @Autowired
    private final BallotRepository ballotRepository;
    @Override
    public void vote(Integer election_id, Integer voter_id, List<CreateBallotRequest> ballot_entries) {

        for (CreateBallotRequest ballot_entry: ballot_entries)
        {
            if (ballot_entry.getCandidatePosition() == 1)
            {
                ballotRepository.saveBallotEntry(
                        election_id,
                        voter_id,
                        ballot_entry.getCandidate_id(),
                        ballot_entry.getCandidatePosition()
                );

                break;
            }
        }

    }
}
