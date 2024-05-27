package com.example.votingService.controller.ballot;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.request.ChangePasswordRequest;
import com.example.votingService.domain.request.CreateBallotRequest;
import com.example.votingService.domain.request.VoteRequest;
import com.example.votingService.domain.user.User;
import com.example.votingService.dto.BallotDto;
import com.example.votingService.dto.ElectionDto;
import com.example.votingService.dto.UserDto;
import com.example.votingService.dto.assembler.BallotDtoAssembler;
import com.example.votingService.dto.assembler.ElectionDtoAssembler;
import com.example.votingService.dto.assembler.LocationDtoAssembler;
import com.example.votingService.dto.assembler.UserDtoAssembler;
import com.example.votingService.service.ballot.BallotService;
import com.example.votingService.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ballots")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BallotController {

    @Autowired
    private final BallotService service;
    @Autowired
    private BallotDtoAssembler ballotDtoAssembler;
    @Autowired
    private ElectionDtoAssembler electionDtoAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<BallotDto>> findAllBallots() {
        List<Ballot> ballots = service.findAllBallots();
        CollectionModel<BallotDto> ballotDtos = ballotDtoAssembler.toCollectionModel(ballots);
        return new ResponseEntity<>(ballotDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BallotDto> findBallotById(@PathVariable Integer id) {
        Ballot ballot = service.findBallotById(id);
        BallotDto ballotDto = ballotDtoAssembler.toModel(ballot);
        return new ResponseEntity<>(ballotDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BallotDto> createBallot(@RequestBody CreateBallotRequest ballot) {
        Ballot newBallot = service.createBallot(ballot);
        BallotDto ballotDto = ballotDtoAssembler.toModel(newBallot);
        return new ResponseEntity<>(ballotDto, HttpStatus.CREATED);
    }

    @PostMapping("/vote")
    public ResponseEntity<?> vote(@RequestBody VoteRequest voteRequest) {
        try {
            service.vote(voteRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateBallot(@RequestBody Ballot ballot) {
        service.updateBallot(ballot);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBallotById(@PathVariable Integer id) {
        service.deleteBallotById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/voter/{id}")
    public ResponseEntity<CollectionModel<BallotDto>> findAllBallotsByVoter(@PathVariable Integer id) {
        List<Ballot> ballots = service.getAllBallotsByVoter(id);
        CollectionModel<BallotDto> ballotDtos = ballotDtoAssembler.toCollectionModel(ballots);
        return new ResponseEntity<>(ballotDtos, HttpStatus.OK);
    }

    @GetMapping("/elections/{id}")
    public ResponseEntity<CollectionModel<ElectionDto>> findAllElectionByVoterId(@PathVariable Integer id) {
        List<Election> elections = service.getAllElectionByVoterId(id);
        CollectionModel<ElectionDto> electionDtos = electionDtoAssembler.toCollectionModel(elections);
        return new ResponseEntity<>(electionDtos, HttpStatus.OK);
    }
}
