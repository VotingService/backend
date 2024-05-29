package com.example.votingService.controller.Admin;

import com.example.votingService.domain.election.Election;
import com.example.votingService.dto.ElectionDto;
import com.example.votingService.dto.assembler.ElectionDtoAssembler;
import com.example.votingService.service.election.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private ElectionService electionService;
    @Autowired
    private ElectionDtoAssembler electionDtoAssembler;

    @PostMapping("/createElection")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ElectionDto> createElection(@RequestBody Election newElection) {
        Election election = electionService.createElection(newElection);
        ElectionDto electionDto = electionDtoAssembler.toModel(election);
        return new ResponseEntity<>(electionDto, HttpStatus.OK);
    }

    @PostMapping("/registerAsCandidate/{electionId}/{candidateId}")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> registerAsCandidate(@PathVariable Integer electionId, @PathVariable Integer candidateId) {
        electionService.registerAsCandidate(electionId, candidateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ElectionDto> updateElection(@RequestBody Election newElection) {
        Election election = electionService.updateElection(newElection);
        ElectionDto electionDto = electionDtoAssembler.toModel(election);
        return new ResponseEntity<>(electionDto, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> delete(Integer id) {
        electionService.deleteElectionById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cancelCandidateShipInElection/{electionId}/{candidateId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> cancelCandidateShipInElection(@PathVariable Integer electionId, @PathVariable Integer candidateId) {
        electionService.cancelCandidateShipInElection(electionId, candidateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
