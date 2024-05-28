package com.example.votingService.controller.election;

import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.request.CreateElectionRequest;
import com.example.votingService.domain.user.User;
import com.example.votingService.dto.ElectionDto;
import com.example.votingService.dto.UserDto;
import com.example.votingService.dto.assembler.ElectionDtoAssembler;
import com.example.votingService.dto.assembler.UserDtoAssembler;
import com.example.votingService.service.election.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/election")
@CrossOrigin(origins = "http://localhost:3000")
public class ElectionController {

    @Autowired
    private ElectionService electionService;
    @Autowired
    private ElectionDtoAssembler electionDtoAssembler;
    @Autowired
    private UserDtoAssembler userDtoAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<ElectionDto>> findAllElections() {
        List<Election> elections = electionService.findAllElections();
        CollectionModel<ElectionDto> electionDtos = electionDtoAssembler.toCollectionModel(elections);
        return new ResponseEntity<>(electionDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectionDto> findElectionById(@PathVariable Integer id) {
        Election election = electionService.findElectionById(id);
        ElectionDto electionDto = electionDtoAssembler.toModel(election);
        return new ResponseEntity<>(electionDto, HttpStatus.OK);
    }

    @GetMapping("/candidates/{id}")
    public ResponseEntity<CollectionModel<UserDto>> getAllCandidatesByElectionId(@PathVariable Integer id) {
        List<User> candidates = electionService.getAllCandidatesByElectionId(id);
        CollectionModel<UserDto> candidateDtos = userDtoAssembler.toCollectionModel(candidates);
        return new ResponseEntity<>(candidateDtos, HttpStatus.OK);
    }

    @GetMapping("/userCanParticipateIn/{id}")
    public ResponseEntity<CollectionModel<ElectionDto>> findAllElectionsThatUserCanParticipateIn(@PathVariable Integer id) {
        List<Election> elections = electionService.findAllElectionsThatUserCanParticipateIn(id);
        CollectionModel<ElectionDto> electionDtos = electionDtoAssembler.toCollectionModel(elections);
        return new ResponseEntity<>(electionDtos, HttpStatus.OK);
    }

}
