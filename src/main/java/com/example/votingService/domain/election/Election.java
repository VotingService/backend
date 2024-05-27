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
    @GeneratedValue
    private Integer id;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean canRetractVote;
    private Integer maxVotes;

//    @Transient
    @Enumerated(EnumType.STRING)
    private VotingStrategyType votingStrategy;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @OneToMany(mappedBy = "election")
    private List<Ballot> ballots;
    @ManyToMany(mappedBy = "elections")
    private Set<User> candidates;

    public Boolean getCanRetractVote() {
        return canRetractVote;
    }

}
