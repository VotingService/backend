package com.example.votingService.controller.election;

import com.example.votingService.domain.election.Election;
import com.example.votingService.service.election.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class ElectionController {

    @Autowired
    private ElectionService electionService;


    @PostMapping("/createElection")
    @PreAuthorize("hasAuthority('admin:create')")
    public Election createElection(Election election) {
        return electionService.createElection(election);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public void delete(Integer id) {
        electionService.deleteElectionById(id);
    }
}
