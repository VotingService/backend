package com.example.votingService.service.election;

import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.request.CreateElectionRequest;
import com.example.votingService.domain.user.User;
import com.example.votingService.dto.CandidateDto;
import com.example.votingService.dto.assembler.LocationDtoAssembler;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.election.ElectionRepository;
import com.example.votingService.repository.location.LocationRepository;
import com.example.votingService.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private BallotRepository ballotRepository;
    @Autowired
    private LocationDtoAssembler locationDtoAssembler;
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
    public List<CandidateDto> getAllCandidatesByElectionId(Integer id) {
        List<User> candidates = electionRepository.getAllCandidatesByElectionId(id);
        int sumPoints = 0;

        Map<Integer, Integer> candidatePoints = new HashMap<>();
        for (User candidate: candidates) {
            Integer candidateId = candidate.getId();
            Integer point = ballotRepository.getPointsOfCandidate(candidateId);
            sumPoints = sumPoints + point;
            candidatePoints.put(candidateId, point);
        }

        List<CandidateDto> candidateDtoList = new ArrayList<>();
        for (User candidate: candidates) {
            Integer candidatePoint = candidatePoints.get(candidate.getId());
            Double pointInPercentage = ((double) candidatePoint / sumPoints) * 100;

            CandidateDto candidateDto = CandidateDto.builder()
                    .id(candidate.getId())
                    .pointInPercentage(pointInPercentage)
                    .firstName(candidate.getFirstName())
                    .lastName(candidate.getLastName())
                    .byFather(candidate.getByFather())
                    .email(candidate.getEmail())
                    .birthDate(candidate.getBirthDate())
                    .location(locationDtoAssembler.toModel(candidate.getLocation()))
                    .build();

            candidateDtoList.add(candidateDto);
        }
        return candidateDtoList.stream()
                .sorted(Comparator.comparing(CandidateDto::getPointInPercentage).reversed())
                .toList();
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
