package com.example.votingService.dto.assembler;

import com.example.votingService.controller.ballot.BallotController;
import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.dto.BallotDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BallotDtoAssembler implements RepresentationModelAssembler<Ballot, BallotDto> {
    @Override
    public BallotDto toModel(Ballot entity) {
        BallotDto ballotDto = BallotDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .electionId(entity.getElection().getId())
                .voterId(entity.getVoter().getId())
                .candidateId(entity.getCandidate().getId())
                .candidatePosition(entity.getCandidatePoint())
                .build();
        Link selfLink = linkTo(methodOn(BallotController.class).findBallotById(ballotDto.getId())).withSelfRel();
        ballotDto.add(selfLink);
        return ballotDto;
    }

    @Override
    public CollectionModel<BallotDto> toCollectionModel(Iterable<? extends Ballot> entities) {
        CollectionModel<BallotDto> ballotDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(BallotController.class).findAllBallots()).withSelfRel();
        ballotDtos.add(selfLink);
        return ballotDtos;
    }
}
