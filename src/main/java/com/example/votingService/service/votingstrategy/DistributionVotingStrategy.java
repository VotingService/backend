package com.example.votingService.service.votingstrategy;

import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.request.ballot.BallotRequest;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.election.ElectionRepository;
import com.example.votingService.util.exception.ElectionNotFoundException;
import com.example.votingService.util.exception.ExceededMaxVotesException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistributionVotingStrategy implements VotingStrategy {
    @Autowired
    private final BallotRepository ballotRepository;
    @Autowired
    private final ElectionRepository electionRepository;

    @Override
    public void vote(Integer electionId, Integer voterId, List<BallotRequest> ballotEntries) throws IllegalArgumentException {

        int total_votes = 0;
        Election election = electionRepository.findById(electionId).orElseThrow(() -> new ElectionNotFoundException(electionId));
        int maxVotes = election.getMaxVotes();

        for (BallotRequest ballotEntry: ballotEntries) {

            total_votes += ballotEntry.getCandidatePoint();

            if (total_votes > maxVotes) {
                throw new ExceededMaxVotesException();
            }
        }

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
