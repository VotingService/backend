package com.example.votingService.service.ballot;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.election.VotingStrategyType;
import com.example.votingService.domain.request.ballot.CreateBallotRequest;
import com.example.votingService.domain.request.VoteRequest;
import com.example.votingService.domain.request.ballot.UpdateBallotRequest;
import com.example.votingService.domain.user.User;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.election.ElectionRepository;
import com.example.votingService.repository.user.UserRepository;
import com.example.votingService.service.votingstrategy.ApprovalVotingStrategy;
import com.example.votingService.service.votingstrategy.DistributionVotingStrategy;
import com.example.votingService.service.votingstrategy.PluralityVotingStrategy;
import com.example.votingService.util.exception.CandidateNotFoundException;
import com.example.votingService.util.exception.ElectionNotFoundException;
import com.example.votingService.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Ballot createBallot(CreateBallotRequest request) {
        Integer election_id = request.getElection_id();
        Integer candidate_id = request.getCandidate_id();
        Integer voter_id = request.getVoter_id();

        var entities = getForeignEntities(election_id, candidate_id, voter_id);

        Ballot ballot = Ballot.builder()
                .election((Election) entities.get("election"))
                .candidate((User) entities.get("candidate"))
                .voter((User) entities.get("voter"))
                .candidatePoint(request.getCandidatePosition())
                .build();

        return repository.save(ballot);
    }

    public void updateBallot(UpdateBallotRequest request) {
        Integer election_id = request.getElection_id();
        Integer candidate_id = request.getCandidate_id();
        Integer voter_id = request.getVoter_id();

        var entities = getForeignEntities(election_id, candidate_id, voter_id);

        if (((Election) entities.get("election")).getCanRetractVote()) {

        }

//        repository.save(ballot);
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

    private Map<String, Object> getForeignEntities(Integer election_id, Integer candidate_id, Integer voter_id) {
        var election = electionRepository.findById(election_id).orElseThrow(() -> new ElectionNotFoundException(election_id));
        var candidate = userRepository.findById(candidate_id).orElseThrow(() -> new CandidateNotFoundException(candidate_id));
        var voter = userRepository.findById(voter_id).orElseThrow(() -> new UserNotFoundException(voter_id));

        Map<String, Object> entities = new HashMap<>();
        entities.put("election", election);
        entities.put("candidate", candidate);
        entities.put("voter", voter);

        return entities;
    }
}

