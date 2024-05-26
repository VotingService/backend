package com.example.votingService.domain.election;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.user.User;
import com.example.votingService.domain.votingstrategy.VotingStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "elections")
public class Election {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany(mappedBy = "elections")
    private Set<User> users = new HashSet<>();

    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean canRetractVote;

    @Transient
    private VotingStrategy votingStrategy;
    @Basic
    @Column(name = "location", nullable = false)
    private String location;

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ballot> ballots = new HashSet<>();

}
