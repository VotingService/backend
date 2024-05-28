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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "country", nullable = false)
    private String country;
    @Basic
    @Column(name = "city", nullable = true)
    private String city;
    @Basic
    @Column(name = "street_name", nullable = true)
    private String streetName;
    @Basic
    @Column(name = "house_number", nullable = true)
    private String houseNumber;
    @Basic
    @Column(name = "post_code", nullable = true)
    private String postCode;
    @OneToMany(mappedBy = "location")
    private List<Election> elections;
    @OneToMany(mappedBy = "location")
    private List<User> users;
}
