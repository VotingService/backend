package com.example.votingService.controller.stats;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.response.FullElectionStatsResponse;
import com.example.votingService.domain.response.SeeUserChoicesResponse;
import com.example.votingService.domain.user.User;
import com.example.votingService.dto.UserDto;
import com.example.votingService.dto.assembler.UserDtoAssembler;
import com.example.votingService.service.stats.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.CollectionModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
public class StatsController {

    @Autowired
    private final StatsService service;
    @Autowired
    private final UserDtoAssembler userDtoAssembler;
    @GetMapping("/fullStats/{election_id}") // +
    public ResponseEntity<List<FullElectionStatsResponse>> getFullElectionStats(@PathVariable Integer election_id) {
        List<FullElectionStatsResponse> responses = service.seeFullElectionStats(election_id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/electionWinner/{election_id}") // +
    public ResponseEntity<CollectionModel<UserDto>> getElectionWinner(@PathVariable Integer election_id) {
        List<User> responses = service.seeElectionWinner(election_id);
        CollectionModel<UserDto> userDtos = userDtoAssembler.toCollectionModel(responses);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/userChoices/{user_id}") // +
    public ResponseEntity<List<SeeUserChoicesResponse>> getUserChoices(@PathVariable Integer user_id) {
        List<SeeUserChoicesResponse> responses = service.seeUserChoices(user_id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/userChoicesInElection/{user_id}/{election_id}")
    public ResponseEntity<List<SeeUserChoicesResponse>> getUserChoicesInElection(@PathVariable Integer election_id, @PathVariable Integer user_id) {
        List<SeeUserChoicesResponse> responses = service.seeUserChoicesInElection(user_id, election_id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}