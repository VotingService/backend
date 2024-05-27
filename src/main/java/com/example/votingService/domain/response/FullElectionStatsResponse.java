package com.example.votingService.domain.response;

import com.example.votingService.dto.LocationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullElectionStatsResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("score")
    private Integer score;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("birthDate")
    private Date birthDate;
    @JsonProperty("location")
    private LocationDto location;
}