package com.example.votingService.service.election;

import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.request.CreateElectionRequest;
import com.example.votingService.domain.user.User;
import com.example.votingService.dto.assembler.ElectionDtoAssembler;
import com.example.votingService.repository.election.ElectionRepository;
import com.example.votingService.repository.location.LocationRepository;
import com.example.votingService.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;
    public List<Election> findAllElections() {
        return electionRepository.findAll();
    }
    public Election findElectionById(Integer id) {
        return electionRepository.findById(id).orElseThrow();
    }

    public Election createElection(CreateElectionRequest electionRequest) {
        Location location = Location.builder()
                .country(electionRequest.getLocation().getCountry())
                .city(electionRequest.getLocation().getCity())
                .build();
        Location savedLocation = locationRepository.save(location);

        Election election = Election.builder()
                .title(electionRequest.getTitle())
                .description(electionRequest.getDescription())
                .startDate(electionRequest.getStartDate())
                .endDate(electionRequest.getEndDate())
                .canRetractVote(electionRequest.getCanRetractVote())
                .votingStrategy(electionRequest.getVotingStrategy())
                .maxVotes(electionRequest.getMaxVotes())
                .location(savedLocation)
                .build();

        return electionRepository.save(election);
    }
    public Election updateElection(Election election) {
        return electionRepository.save(election);
    }
    public List<User> getAllCandidatesByElectionId(Integer id) {
        return electionRepository.getAllCandidatesByElectionId(id);
    }

    public void deleteElectionById(Integer id) {
        electionRepository.deleteById(id);
    }

    public void cancelCandidateShipInElection(Integer electionId, Integer candidateId) {
        electionRepository.removeCandidateFromElection(electionId, candidateId);
    }

    public void registerAsCandidate(Integer electionId, Integer candidateId) {
        electionRepository.addCandidateToElection(electionId, candidateId);
    }

    public List<Election> findAllElectionsThatUserCanParticipateIn(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        Location location = user.getLocation();
        List<Election> elections = this.findAllElections();

        List<Election> userElections = new ArrayList<>();
        for (Election election : elections) {
            Location electionLocation = election.getLocation();
            if ((electionLocation.getCountry() == null || electionLocation.getCountry().equals(location.getCountry()))
                    && (electionLocation.getCity() == null || electionLocation.getCity().equals(location.getCity()))
                    && (electionLocation.getStreetName() == null || electionLocation.getStreetName().equals(location.getStreetName()))
                    && (electionLocation.getHouseNumber() == null || electionLocation.getHouseNumber().equals(location.getHouseNumber()))
                    && (electionLocation.getPostCode() == null || electionLocation.getPostCode().equals(location.getPostCode()))) {
                userElections.add(election);
            }
        }

        return userElections;
    }
}
