package com.example.votingService.service.votingstrategy;

import com.example.votingService.domain.request.ballot.BallotRequest;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.util.exception.BadCandidatePointException;
import com.example.votingService.util.exception.PluralityVoteException;
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
    public void vote(Integer electionId, Integer voterId, List<BallotRequest> ballotEntries) throws IllegalArgumentException {

        int total_votes = 0;

        for (BallotRequest ballotEntry: ballotEntries) {
            Integer candidate_point = ballotEntry.getCandidatePoint();

            if (candidate_point != 0 && candidate_point != 1) {
                throw new BadCandidatePointException();
            }

            total_votes += candidate_point;

            if (total_votes > 1) {
                throw new PluralityVoteException();
            }
        }

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
