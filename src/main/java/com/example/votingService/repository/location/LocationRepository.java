package com.example.votingService.repository.location;

import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    public Location getLocationsByCountryAndCityAndStreetNameAndHouseNumberAndPostCode(String country, String city, String streetName, String houseNumber, String postCode);
}