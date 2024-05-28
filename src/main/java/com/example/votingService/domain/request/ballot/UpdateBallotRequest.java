package com.example.votingService.domain.request.ballot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBallotRequest {

    private Integer id;
    private Integer election_id;
    private Integer voter_id;
    private Integer candidate_id;
    private Integer candidatePosition;
}