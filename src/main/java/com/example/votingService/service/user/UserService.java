package com.example.votingService.service.user;

import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.request.ChangePasswordRequest;
import com.example.votingService.domain.request.UpdateUserRequest;
import com.example.votingService.domain.user.Role;
import com.example.votingService.domain.user.User;
import com.example.votingService.repository.location.LocationRepository;
import com.example.votingService.repository.user.UserRepository;
import com.example.votingService.util.exception.UserNotFoundException;
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
    public User updateUser(UpdateUserRequest request) {
        var location = Location.builder()
                .country(request.getLocation().getCountry())
                .city(request.getLocation().getCity())
                .streetName(request.getLocation().getStreetName())
                .houseNumber(request.getLocation().getHouseNumber())
                .postCode(request.getLocation().getPostCode())
                .build();

        var savedLocation = locationRepository.save(location);
        request.setLocation(savedLocation);

        repository.updateUser(request.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getByFather(),
                request.getBirthDate(),
                request.getLocation());

        return repository.findById(request.getId()).orElseThrow();
    }

    public void deleteUserById(Integer id) {
        repository.deleteById(id);
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        repository.save(user);
    }
}
