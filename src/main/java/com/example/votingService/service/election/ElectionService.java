package com.example.votingService.service.election;

import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.election.VotingStrategyType;
import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.user.User;
import com.example.votingService.dto.CandidateDto;
import com.example.votingService.dto.assembler.LocationDtoAssembler;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.election.ElectionRepository;
import com.example.votingService.repository.location.LocationRepository;
import com.example.votingService.repository.user.UserRepository;
import com.example.votingService.util.exception.ElectionNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return electionRepository.findById(id).orElseThrow(() -> new ElectionNotFoundException(id));
    }

    public Election createElection(Election electionRequest) {
        Location location = electionRequest.getLocation();

        location = locationRepository.getLocationsByCountryAndCityAndStreetNameAndHouseNumberAndPostCode(location.getCountry(),
                location.getCity(), location.getStreetName(), location.getHouseNumber(), location.getPostCode());

        if (location == null) {
            location = electionRequest.getLocation();

            location = Location.builder()
                    .country(location.getCountry())
                    .city(location.getCity())
                    .streetName(location.getStreetName())
                    .houseNumber(location.getHouseNumber())
                    .postCode(location.getPostCode())
                    .build();

            location = locationRepository.save(location);
        }

        int maxVotes = 0;
        if (electionRequest.getVotingStrategy() == VotingStrategyType.ApprovalVoting) {
            maxVotes = 1;
        } else if (electionRequest.getVotingStrategy() == VotingStrategyType.PluralityVoting) {
            maxVotes = 1;
        } else {
            maxVotes = 100;
        }

        Election election = Election.builder()
                .title(electionRequest.getTitle())
                .description(electionRequest.getDescription())
                .startDate(electionRequest.getStartDate())
                .endDate(electionRequest.getEndDate())
                .canRetractVote(electionRequest.getCanRetractVote())
                .votingStrategy(electionRequest.getVotingStrategy())
                .maxVotes(maxVotes)
                .location(location)
                .build();

        return electionRepository.save(election);
    }

    public Election updateElection(Election election) {
        Location location = election.getLocation();

        location = locationRepository.getLocationsByCountryAndCityAndStreetNameAndHouseNumberAndPostCode(location.getCountry(),
                location.getCity(), location.getStreetName(), location.getHouseNumber(), location.getPostCode());

        if (location == null) {
            location = election.getLocation();

            location = Location.builder()
                    .country(location.getCountry())
                    .city(location.getCity())
                    .streetName(location.getStreetName())
                    .houseNumber(location.getHouseNumber())
                    .postCode(location.getPostCode())
                    .build();

            location = locationRepository.save(location);
        }
        election.setLocation(location);
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
                    .photoUrl(candidate.getPhotoUrl())
                    .description(candidate.getDescription())
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

    @Transactional
    public void cancelCandidateShipInElection(Integer electionId, Integer candidateId) {
        electionRepository.removeCandidateFromElection(electionId, candidateId);
    }

    @Transactional
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
