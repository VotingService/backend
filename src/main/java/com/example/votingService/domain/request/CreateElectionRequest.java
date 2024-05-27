package com.example.votingService.domain.request;

import com.example.votingService.domain.election.VotingStrategyType;
import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateElectionRequest {

    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean canRetractVote;
    private VotingStrategyType votingStrategy;
    private Integer maxVotes;
    private Location location;
    public Boolean getCanRetractVote() {
        return canRetractVote;
    }
}
