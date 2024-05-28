package com.example.votingService.domain.request;

import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private Integer id;
    private String firstName;
    private String lastName;
    private String byFather;
    private String photoUrl;
    private String description;
    private Date birthDate;
    private Location location;
}
