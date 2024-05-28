package com.example.votingService.service.votingstrategy;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.request.ballot.BallotRequest;
import com.example.votingService.repository.ballot.BallotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalVotingStrategy implements VotingStrategy{

    @Autowired
    private final BallotRepository ballotRepository;
    @Override
    public void vote(Integer electionId, Integer voterId, List<BallotRequest> ballotEntries) {
        List<Ballot> ballots = ballotRepository.getAllBallotsOfVoterInElection(voterId, electionId);

        for (BallotRequest ballotEntry: ballotEntries)
        {
            ballotRepository.saveBallotEntry(
                    electionId,
                    voterId,
                    ballotEntry.getCandidateId(),
                    ballotEntry.getCandidatePoint()
            );
        }
    }
}
