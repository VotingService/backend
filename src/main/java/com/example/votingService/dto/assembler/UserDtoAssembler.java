package com.example.votingService.dto.assembler;

import com.example.votingService.dto.LocationDto;
import com.example.votingService.dto.UserDto;
import com.example.votingService.controller.user.UserController;
import com.example.votingService.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserDtoAssembler implements RepresentationModelAssembler<User, UserDto> {
    @Autowired
    private LocationDtoAssembler locationDtoAssembler;
    @Override
    public UserDto toModel(User entity) {

        UserDto userDto = UserDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .birthDate(entity.getBirthDate())
                .location(locationDtoAssembler.toModel(entity.getLocation()))
                .build();
        Link selfLink = linkTo(methodOn(UserController.class).findUserById(userDto.getId())).withSelfRel();
        userDto.add(selfLink);
        return userDto;
    }

    @Override
    public CollectionModel<UserDto> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserDto> userDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(UserController.class).findAllUsers()).withSelfRel();
        userDtos.add(selfLink);
        return userDtos;
    }
}
