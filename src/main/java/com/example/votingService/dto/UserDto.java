package com.example.votingService.dto;

import com.example.votingService.domain.location.Location;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "user", collectionRelation = "users")
public class UserDto extends RepresentationModel<UserDto> {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String byFather;
    private final String email;
    private final String password;
    private final Date birthDate;
    private final LocationDto location;
}
