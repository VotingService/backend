package com.example.votingService.dto.assembler;

import com.example.votingService.controller.election.ElectionController;
import com.example.votingService.domain.election.Election;
import com.example.votingService.dto.ElectionDto;
import com.example.votingService.dto.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ElectionDtoAssembler implements RepresentationModelAssembler<Election, ElectionDto> {
    @Autowired
    private LocationDtoAssembler locationDtoAssembler;
    @Override
    public ElectionDto toModel(Election entity) {

        ElectionDto electionDto = ElectionDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .canRetractVote(entity.getCanRetractVote())
                .votingStrategy(entity.getVotingStrategy())
                .location(locationDtoAssembler.toModel(entity.getLocation()))
                .build();
        Link selfLink = linkTo(methodOn(ElectionController.class).findElectionById(electionDto.getId())).withSelfRel();
        electionDto.add(selfLink);
        return electionDto;
    }

    @Override
    public CollectionModel<ElectionDto> toCollectionModel(Iterable<? extends Election> entities) {
        CollectionModel<ElectionDto> electionDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(ElectionController.class).findAllElections()).withSelfRel();
        electionDtos.add(selfLink);
        return electionDtos;
    }
}
