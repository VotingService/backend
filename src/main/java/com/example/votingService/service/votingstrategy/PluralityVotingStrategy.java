package com.example.votingService.service.votingstrategy;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.election.ElectionRepository;
import com.example.votingService.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
    public void vote(Integer election_id, Integer voter_id, Integer candidate_id, Integer candidatePosition) {
        List<Ballot> ballots = ballotRepository.getAllBallotsOfVoterInElection(voter_id, election_id);

        HashMap<Integer, Integer> candidates = new HashMap<>();
        for(Ballot ballot: ballots){
            candidates.put(candidate_id, candidatePosition);
        }

        var election = electionRepository.findById(election_id).orElseThrow();
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        if (now.isBefore(election.getEndDate()) && now.isAfter(election.getStartDate())) {
            if (election.getCanRetractVote()){
                if (candidates.get(candidate_id) == null || !Objects.equals(candidates.get(candidate_id), candidatePosition)) {
                    var candidate = userRepository.findById(candidate_id).orElseThrow();
                    var voter = userRepository.findById(voter_id).orElseThrow();
                    Ballot ballot = Ballot.builder()
                            .election(election)
                            .candidate(candidate)
                            .voter(voter)
                            .candidatePosition(candidatePosition)
                            .build();
                    ballotRepository.save(ballot);
                }
            } else {
                if (candidates.get(candidate_id) == null) {
                    var candidate = userRepository.findById(candidate_id).orElseThrow();
                    var voter = userRepository.findById(voter_id).orElseThrow();
                    Ballot ballot = Ballot.builder()
                            .election(election)
                            .candidate(candidate)
                            .voter(voter)
                            .candidatePosition(candidatePosition)
                            .build();
                    ballotRepository.save(ballot);
                }
            }
        }
    }
}
