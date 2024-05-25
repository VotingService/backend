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
@Table(schema = "public", name = "ballots")
public class Ballot {
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
    @Column(name = "candidate_position", nullable = false)
    private int candidatePosition;
    @ManyToOne
    @JoinColumn(name = "election_id", referencedColumnName = "id")
    private Election election;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User voter;
    @ManyToOne
    @JoinColumn(name = "candidate_user_id", referencedColumnName = "id")
    private User candidate;
}
