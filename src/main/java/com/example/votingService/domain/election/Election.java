package com.example.votingService.domain.election;

import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.user.User;
import com.example.votingService.domain.ballot.Ballot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public", name = "elections")
public class Election {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Basic
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @Basic
    @Column(name = "can_retract_vote", nullable = false)
    private boolean canRetractVote;
    @Basic
    @Column(name = "strategy", nullable = false)
    private VotingStrategy strategy;
    @OneToMany(mappedBy = "election")
    private List<Ballot> ballots;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @ManyToMany(mappedBy = "elections")
    private Set<User> candidates;
}
