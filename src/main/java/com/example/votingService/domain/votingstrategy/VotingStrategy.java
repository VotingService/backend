package com.example.votingService.domain.votingstrategy;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "votingStrategyType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ApprovalVotingStrategy.class, name = "APPROVAL_VOTING_STRATEGY"),
        @JsonSubTypes.Type(value = InstantRunoffVotingStrategy.class, name = "INSTANT_RUN_OFF_VOTING_STRATEGY"),
        @JsonSubTypes.Type(value = PluralityVotingStrategy.class, name = "PLURALITY_VOTING_STRATEGY"),
})
public interface VotingStrategy {

    VotingStrategyType votingStrategyType = null;

    enum VotingStrategyType {
        APPROVAL_VOTING_STRATEGY,
        INSTANT_RUN_OFF_VOTING_STRATEGY,
        PLURALITY_VOTING_STRATEGY
    }
}
