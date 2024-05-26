package com.example.votingService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "location", collectionRelation = "locations")
public class LocationDto extends RepresentationModel<LocationDto> {
    private final String country;
    private final String city;
    private final String streetName;
    private final String houseNumber;
    private final String postCode;
}
