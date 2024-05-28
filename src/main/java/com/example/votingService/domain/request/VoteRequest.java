package com.example.votingService.domain.request;

import com.example.votingService.domain.request.ballot.BallotRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {

    @JsonProperty("election_id")
    private Integer electionId;
    @JsonProperty("voter_id")
    private Integer voterId;
    @JsonProperty("ballot_entries")
    private ArrayList<BallotRequest> ballotEntries;
}
