package com.example.votingService.dto;

import com.example.votingService.domain.election.VotingStrategyType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "election", collectionRelation = "elections")
public class ElectionDto extends RepresentationModel<ElectionDto> {
    private final Integer id;
    private final String title;
    private final String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime endDate;
    private final boolean canRetractVote;
    private final VotingStrategyType votingStrategy;
    private final Integer maxVotes;
    private final LocationDto location;
}
