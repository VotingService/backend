package com.example.votingService.domain.user;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String password;
    private Date birthDate;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "voter")
    private List<Ballot> ballotsAsVoter;
    @OneToMany(mappedBy = "candidate")
    private List<Ballot> ballotsAsCandidate;

    @ManyToMany
    @JoinTable(
            name = "candidate_election",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "election_id")
    )
    private Set<Election> elections = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
