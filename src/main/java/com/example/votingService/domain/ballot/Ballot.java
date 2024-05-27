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
@Table(name = "ballots")
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
    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User voter;
    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private User candidate;

    @Basic
    @Column(name = "candidate_position", nullable = false)
    private Integer candidatePosition;
}
