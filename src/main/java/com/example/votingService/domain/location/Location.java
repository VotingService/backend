package com.example.votingService.domain.location;

import com.example.votingService.domain.user.User;
import com.example.votingService.domain.election.Election;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "country", nullable = false, length = -1)
    private String country;
    @Basic
    @Column(name = "city", nullable = true, length = -1)
    private String city;
    @Basic
    @Column(name = "street_name", nullable = true, length = -1)
    private String streetName;
    @Basic
    @Column(name = "house_number", nullable = true, length = -1)
    private String houseNumber;
    @Basic
    @Column(name = "post_code", nullable = true, length = -1)
    private String postCode;
    @OneToMany(mappedBy = "location")
    private List<Election> elections;
    @OneToMany(mappedBy = "location")
    private List<User> users;
}
