package com.example.votingService.dto;

import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;
import java.util.Date;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "ballot", collectionRelation = "ballots")
public class BallotDto extends RepresentationModel<BallotDto> {
    private final Integer id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer electionId;
    private Integer voterId;
    private Integer candidateId;
    private Integer candidatePosition;
}
