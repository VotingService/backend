package com.example.votingService.service.stats;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.response.SeeUserChoicesResponse;
import com.example.votingService.domain.response.FullElectionStatsResponse;
import com.example.votingService.domain.user.User;
import com.example.votingService.dto.assembler.ElectionDtoAssembler;
import com.example.votingService.dto.assembler.LocationDtoAssembler;
import com.example.votingService.dto.assembler.UserDtoAssembler;
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
public class StatsService {

    @Autowired
    private final BallotRepository ballotRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final LocationDtoAssembler locationDtoAssembler;
    @Autowired
    private final ElectionDtoAssembler electionDtoAssembler;
    @Autowired
    private final UserDtoAssembler userDtoAssembler;

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

    public List<FullElectionStatsResponse> seeFullElectionStats(Integer election_id) {
        List<Ballot> ballots = ballotRepository.getAllBallotsByElectionId(election_id);

        HashMap<Integer, Integer> candidateStats = new HashMap<>();
        for(Ballot ballot: ballots){
            Integer candidate_id = ballot.getCandidate().getId();
            candidateStats.put(candidate_id, candidateStats.get(candidate_id) + ballot.getCandidatePosition());
        }

        List<FullElectionStatsResponse> response = new ArrayList<>();
        for (HashMap.Entry<Integer,Integer> candidateStat : candidateStats.entrySet()) {
            User candidate = userRepository.findById(candidateStat.getKey()).orElseThrow();
            FullElectionStatsResponse electionStatsResponse = FullElectionStatsResponse.builder()
                    .id(candidate.getId())
                    .score(candidateStat.getValue())
                    .firstname(candidate.getFirstname())
                    .lastname(candidate.getLastname())
                    .email(candidate.getEmail())
                    .password(candidate.getPassword())
                    .birthDate(candidate.getBirthDate())
                    .location(locationDtoAssembler.toModel(candidate.getLocation()))
                    .build();
            response.add(electionStatsResponse);
        }
        return response;
    }

    public List<SeeUserChoicesResponse> seeUserChoices(Integer user_id) {
        List<Ballot> ballots = ballotRepository.getAllBallotsByVoterId(user_id);
        List<SeeUserChoicesResponse> responseList = new ArrayList<>();
        for (Ballot ballot: ballots) {
            SeeUserChoicesResponse response = SeeUserChoicesResponse.builder()
                    .election(electionDtoAssembler.toModel(ballot.getElection()))
                    .candidate(userDtoAssembler.toModel(ballot.getCandidate()))
                    .voter(userDtoAssembler.toModel(ballot.getVoter()))
                    .candidatePosition(ballot.getCandidatePosition())
                    .build();
            responseList.add(response);
        }
        return responseList;
    }

    public List<SeeUserChoicesResponse> seeUserChoicesInElection(Integer user_id, Integer election_id) {
        List<Ballot> ballots = ballotRepository.getAllBallotsByVoterIdAndElectionId(user_id, election_id);
        List<SeeUserChoicesResponse> responseList = new ArrayList<>();
        for (Ballot ballot: ballots) {
            SeeUserChoicesResponse response = SeeUserChoicesResponse.builder()
                    .election(electionDtoAssembler.toModel(ballot.getElection()))
                    .candidate(userDtoAssembler.toModel(ballot.getCandidate()))
                    .voter(userDtoAssembler.toModel(ballot.getVoter()))
                    .candidatePosition(ballot.getCandidatePosition())
                    .build();
            responseList.add(response);
        }
        return responseList;
    }
}
