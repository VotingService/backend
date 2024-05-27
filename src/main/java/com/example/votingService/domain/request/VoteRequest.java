package com.example.votingService.domain.request;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {

    private Integer election_id;
    private Integer voter_id;
    private ArrayList<CreateBallotRequest> ballot_entries;
}
