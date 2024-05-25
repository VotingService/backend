package com.example.votingService.domain.user;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.location.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public", name = "users")
public class User implements UserDetails {
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
    @Column(name = "firstname", nullable = false, length = -1)
    private String firstname;
    @Basic
    @Column(name = "lastname", nullable = false, length = -1)
    private String lastname;
    @Basic
    @Column(name = "email", nullable = false, length = -1)
    private String email;
    @Basic
    @Column(name = "password", nullable = false, length = -1)
    private String password;
    @Basic
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Basic
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
//    @OneToMany(mappedBy = "voter")
//    private List<Ballot> ballotsAsVoter;
//    @OneToMany(mappedBy = "candidate")
//    private List<Ballot> ballotsAsCandidate;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @ManyToMany
    @JoinTable(name = "candidate_election", catalog = "vgzkuacl", schema = "public", joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "election_id", referencedColumnName = "id", nullable = false))
    private Set<Election> elections;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
