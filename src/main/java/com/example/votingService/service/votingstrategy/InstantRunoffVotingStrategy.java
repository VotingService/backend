package com.example.votingService.service.votingstrategy;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.user.User;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.election.ElectionRepository;
import com.example.votingService.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstantRunoffVotingStrategy implements VotingStrategy{

    @Autowired
    private final BallotRepository ballotRepository;
    @Autowired
    private final ElectionRepository electionRepository;
    @Autowired
    private final UserRepository userRepository;
    @Override
    public void vote(Integer election_id, Integer voter_id, Integer candidate_id, Integer candidatePosition) {

    }

    @Override
    public List<User> seeElectionWinner(Integer election_id) {
        List<Ballot> ballots = ballotRepository.getAllBallotsByElectionId(election_id);

        HashMap<Integer, Integer> candidates = new HashMap<>();
        for(Ballot ballot: ballots){
            Integer candidate_id = ballot.getCandidate().getId();
            candidates.put(candidate_id, candidates.get(candidate_id) + ballot.getCandidatePosition());
        }

        List<User> users = new ArrayList<>();
        int maxVotes = 0;
        for (HashMap.Entry<Integer, Integer> candidate : candidates.entrySet()) {
            Integer num_of_votes = candidate.getValue();
            if (num_of_votes > maxVotes) {
                users.clear();
                User new_winner = userRepository.findById(candidate.getKey()).orElseThrow();
                users.add(new_winner);
                maxVotes = num_of_votes;
            } else if (num_of_votes == maxVotes) {
                User new_winner = userRepository.findById(candidate.getKey()).orElseThrow();
                users.add(new_winner);
            }
        }
        return users;
    };
}
