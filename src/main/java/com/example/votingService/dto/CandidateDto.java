package com.example.votingService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "candidate", collectionRelation = "candidates")
public class CandidateDto extends RepresentationModel<CandidateDto> {
    private final Integer id;
    private final Double pointInPercentage;
    private final String firstName;
    private final String lastName;
    private final String byFather;
    private final String email;
    private final Date birthDate;
    private final LocationDto location;
}

