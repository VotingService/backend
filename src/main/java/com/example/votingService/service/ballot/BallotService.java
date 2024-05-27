package com.example.votingService.service.ballot;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.election.VotingStrategyType;
import com.example.votingService.domain.request.CreateBallotRequest;
import com.example.votingService.domain.request.VoteRequest;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.election.ElectionRepository;
import com.example.votingService.repository.user.UserRepository;
import com.example.votingService.service.votingstrategy.ApprovalVotingStrategy;
import com.example.votingService.service.votingstrategy.DistributionVotingStrategy;
import com.example.votingService.service.votingstrategy.PluralityVotingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return repository.findById(id).orElseThrow();
    }

    public Ballot createBallot(CreateBallotRequest ballotRequest) {
        var election = electionRepository.findById(ballotRequest.getElection_id()).orElseThrow();
        var candidate = userRepository.findById(ballotRequest.getCandidate_id()).orElseThrow();
        var voter = userRepository.findById(ballotRequest.getVoter_id()).orElseThrow();

        Ballot ballot = Ballot.builder()
                .election(election)
                .candidate(candidate)
                .voter(voter)
                .candidatePosition(ballotRequest.getCandidatePosition())
                .build();
        return repository.save(ballot);
    }

    public Ballot updateBallot(Ballot ballot) {
        return repository.save(ballot);
    }

    public void deleteBallotById(Integer id) {
        repository.deleteById(id);
    }

    public List<Ballot> getAllBallotsByVoter(Integer id){
        return repository.getAllBallotsByVoterId(id);
    }

    public List<Election> getAllElectionByVoterId(Integer id) {
        return repository.getAllElectionByVoterId(id);
    };


    public void vote(VoteRequest voteRequest) {
        Integer election_id = voteRequest.getElection_id();
        Integer voter_id = voteRequest.getVoter_id();
        List<CreateBallotRequest> ballot_entries = voteRequest.getBallot_entries();

        VotingStrategyType votingStrategyType = electionRepository.findById(election_id).orElseThrow().getVotingStrategy();
        if (votingStrategyType == VotingStrategyType.ApprovalVoting) {
            ApprovalVotingStrategy approvalVotingStrategy = new ApprovalVotingStrategy(repository);
            approvalVotingStrategy.vote(election_id, voter_id, ballot_entries);
        } else if (votingStrategyType == VotingStrategyType.PluralityVoting) {
            PluralityVotingStrategy pluralityVotingStrategy = new PluralityVotingStrategy(repository);
            pluralityVotingStrategy.vote(election_id, voter_id, ballot_entries);
        } else if (votingStrategyType == VotingStrategyType.DistributionVoting){
            DistributionVotingStrategy distributionVotingStrategy = new DistributionVotingStrategy(repository);
            distributionVotingStrategy.vote(election_id, voter_id, ballot_entries);
        }

    }
}

