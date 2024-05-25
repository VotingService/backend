package com.example.votingService.service.election;

import com.example.votingService.domain.election.Election;
import com.example.votingService.repository.election.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    public Election createElection(Election election) {
        return electionRepository.save(election);
    }

    public void deleteElectionById(Integer id) {
        electionRepository.deleteById(id);
    }

    public Election cancelCandidatShipInElection() {
        //TODO
        return null;
    }

    public Election registerAsCandidate() {
        //TODO
        return null;
    }


}
