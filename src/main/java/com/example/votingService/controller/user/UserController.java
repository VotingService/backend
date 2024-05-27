package com.example.votingService.controller.user;

import com.example.votingService.domain.election.Election;
import com.example.votingService.domain.request.ChangePasswordRequest;
import com.example.votingService.domain.user.User;
import com.example.votingService.dto.UserDto;
import com.example.votingService.dto.assembler.LocationDtoAssembler;
import com.example.votingService.dto.assembler.UserDtoAssembler;
import com.example.votingService.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private final UserService service;
    @Autowired
    private UserDtoAssembler userDtoAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<UserDto>> findAllUsers() {
        List<User> users = service.findAllUsers();
        CollectionModel<UserDto> userDtos = userDtoAssembler.toCollectionModel(users);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Integer id) {
        User user = service.findUserById(id);
        UserDto userDto = userDtoAssembler.toModel(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        User new_user = service.createUser(user);
        UserDto userDto = userDtoAssembler.toModel(new_user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        service.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer id) {
        service.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
