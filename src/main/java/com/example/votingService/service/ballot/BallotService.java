package com.example.votingService.service.ballot;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.election.VotingStrategyType;
import com.example.votingService.domain.request.ballot.BallotRequest;
import com.example.votingService.domain.request.VoteRequest;
import com.example.votingService.domain.request.ballot.UpdateBallotRequest;
import com.example.votingService.domain.user.User;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.election.ElectionRepository;
import com.example.votingService.repository.user.UserRepository;
import com.example.votingService.service.votingstrategy.ApprovalVotingStrategy;
import com.example.votingService.service.votingstrategy.DistributionVotingStrategy;
import com.example.votingService.service.votingstrategy.PluralityVotingStrategy;
import com.example.votingService.util.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BallotService {
    @Autowired
    private final BallotRepository repository;
    @Autowired
    private final ElectionRepository electionRepository;
    @Autowired
    private final UserRepository userRepository;

    public List<Ballot> findAllBallots() {
        return repository.findAll();
    }

    public Ballot findBallotById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BallotNotFoundException(id));
    }

    @Transactional
    public void updateBallot(UpdateBallotRequest request) throws RuntimeException {
        Integer electionId = request.getElectionId();
        Integer candidateId = request.getCandidateId();
        Integer voterId = request.getVoterId();

        var election = electionRepository.findById(electionId).orElseThrow(() -> new ElectionNotFoundException(electionId));

        if (election.getCanRetractVote()) {
            repository.update(request.getId(), electionId, voterId, candidateId, request.getCandidatePoint());
        } else {
            throw new CanNotRetractVoteException(electionId);
        }
    }

    public void deleteBallotById(Integer id) {
        repository.deleteById(id);
    }

    public List<Ballot> getAllBallotsByVoterId(Integer id){
        return repository.getAllBallotsByVoterId(id);
    }

    public List<Election> getAllElectionByVoterId(Integer id) {
        return repository.getAllElectionByVoterId(id);
    }


    @Transactional
    public void vote(VoteRequest voteRequest) throws IllegalArgumentException {
        // For Ostap to check
        Integer electionId = voteRequest.getElectionId();
        Integer voterId = voteRequest.getVoterId();
        List<BallotRequest> ballotEntries = voteRequest.getBallotEntries();

        Election election = electionRepository.findById(electionId).orElseThrow(() -> new ElectionNotFoundException(electionId));
        VotingStrategyType votingStrategyType = election.getVotingStrategy();
        if (votingStrategyType == VotingStrategyType.ApprovalVoting) {
            ApprovalVotingStrategy approvalVotingStrategy = new ApprovalVotingStrategy(repository);
            approvalVotingStrategy.vote(electionId, voterId, ballotEntries);
        } else if (votingStrategyType == VotingStrategyType.PluralityVoting) {
            PluralityVotingStrategy pluralityVotingStrategy = new PluralityVotingStrategy(repository);
            pluralityVotingStrategy.vote(electionId, voterId, ballotEntries);
        } else if (votingStrategyType == VotingStrategyType.DistributionVoting) {
            try {
                DistributionVotingStrategy distributionVotingStrategy = new DistributionVotingStrategy(repository, electionRepository);
                distributionVotingStrategy.vote(electionId, voterId, ballotEntries);
            } catch (IllegalArgumentException e) {
                throw e;
            }
        }

    }
}

