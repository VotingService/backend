package com.example.votingService.domain.election;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.user.User;
import com.example.votingService.domain.votingstrategy.VotingStrategy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_election")
public class Election {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany(mappedBy = "elections")
    private Set<User> users = new HashSet<>();

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean canRetractVote;

    @Transient
    private VotingStrategy votingStrategy;
    private String location;

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ballot> ballots = new HashSet<>();

}
