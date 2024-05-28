package com.example.votingService.service.votingstrategy;

import com.example.votingService.domain.request.ballot.BallotRequest;
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
    public void vote(Integer electionId, Integer voterId, List<BallotRequest> ballotEntries) {

        for (BallotRequest ballotEntry: ballotEntries)
        {
            if (ballotEntry.getCandidateId() == 1)
            {
                ballotRepository.saveBallotEntry(
                        electionId,
                        voterId,
                        ballotEntry.getCandidateId(),
                        ballotEntry.getCandidatePoint()
                );

                break;
            }
        }

    }
}
