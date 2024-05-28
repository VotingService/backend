package com.example.votingService.domain.request.ballot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BallotRequest {

    @JsonProperty("election_id")
    private Integer electionId;
    @JsonProperty("voter_id")
    private Integer voterId;
    @JsonProperty("candidate_id")
    private Integer candidateId;
    @JsonProperty("candidate_point")
    private Integer candidatePoint;
}
