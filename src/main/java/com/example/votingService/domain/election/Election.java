package com.example.votingService.domain.election;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "elections")
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    @Basic
    @Column(name = "title", nullable = false)
    private String title;
    @Basic
    @Column(name = "description", nullable = false)
    private String description;
    @Basic
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Basic
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    @Basic
    @Column(name = "can_retract_vote", nullable = false)
    private boolean canRetractVote;
    @Basic
    @Column(name = "max_votes", nullable = true)
    private Integer maxVotes;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "voting_strategy", nullable = false)
    private VotingStrategyType votingStrategy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location location;

    @OneToMany(mappedBy = "election")
    private List<Ballot> ballots;
    @ManyToMany(mappedBy = "elections")
    private List<User> candidates;

    public Boolean getCanRetractVote() {
        return canRetractVote;
    }

}
