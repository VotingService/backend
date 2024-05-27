package com.example.votingService.dto.assembler;

import com.example.votingService.dto.LocationDto;
import com.example.votingService.domain.location.Location;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class LocationDtoAssembler implements RepresentationModelAssembler<Location, LocationDto> {
    @Override
    public LocationDto toModel(Location entity) {
        LocationDto locationDto = LocationDto.builder()
                .country(entity.getCountry())
                .city(entity.getCity())
                .streetName(entity.getStreetName())
                .houseNumber(entity.getHouseNumber())
                .postCode(entity.getPostCode())
                .build();
//        Link selfLink = linkTo(methodOn(UserController.class).getUser(userDto.getId())).withSelfRel();
//        userDto.add(selfLink);
        return locationDto;
    }
}
