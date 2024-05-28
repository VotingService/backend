package com.example.votingService.service.user;

import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.request.ChangePasswordRequest;
import com.example.votingService.domain.user.User;
import com.example.votingService.repository.location.LocationRepository;
import com.example.votingService.repository.user.UserRepository;
import com.example.votingService.util.exception.UserNotFoundException;
import com.example.votingService.util.exception.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final LocationRepository locationRepository;

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public User findUserById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User updateUser(User request) {
        Location location = request.getLocation();

        location = locationRepository.getLocationsByCountryAndCityAndStreetNameAndHouseNumberAndPostCode(location.getCountry(),
                location.getCity(), location.getStreetName(), location.getHouseNumber(), location.getPostCode());

        if (location == null) {
            location = request.getLocation();

            location = Location.builder()
                    .country(location.getCountry())
                    .city(location.getCity())
                    .streetName(location.getStreetName())
                    .houseNumber(location.getHouseNumber())
                    .postCode(location.getPostCode())
                    .build();

            location = locationRepository.save(location);
        }

        repository.updateUser(request.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getByFather(),
                request.getPhotoUrl(),
                request.getDescription(),
                request.getBirthDate(),
                location);

        return repository.findById(request.getId()).orElseThrow(() -> new UserNotFoundException(request.getId()));
    }

    public void deleteUserById(Integer id) {
        repository.deleteById(id);
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new WrongPasswordException();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        repository.save(user);
    }
}
