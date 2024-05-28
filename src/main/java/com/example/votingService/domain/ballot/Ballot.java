package com.example.votingService.domain.ballot;


import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ballots", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"election_id", "user_id", "candidate_id"})
})
public class Ballot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "election_id", nullable = false, referencedColumnName = "id")
    private Election election;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User voter;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "candidate_id", nullable = false, referencedColumnName = "id")
    private User candidate;

    @Basic
    @Column(name = "candidate_point", nullable = false)
    private Integer candidatePoint;
}
