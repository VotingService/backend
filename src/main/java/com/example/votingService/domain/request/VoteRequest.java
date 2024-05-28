package com.example.votingService.domain.request;

import com.example.votingService.domain.request.ballot.CreateBallotRequest;
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

    private Integer election_id;
    private Integer voter_id;
    private ArrayList<CreateBallotRequest> ballot_entries;
}
