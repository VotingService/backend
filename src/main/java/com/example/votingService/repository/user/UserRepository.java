package com.example.votingService.repository.user;

import com.example.votingService.domain.location.Location;
import com.example.votingService.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsCurrentAccountByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.byFather = :byFather, " +
            "u.birthDate = :birthDate, u.location = :location WHERE u.id = :id")
    public void updateUser(@Param("id") Integer id,
                           @Param("firstName") String firstName,
                           @Param("lastName") String lastName,
                           @Param("byFather") String byFather,
                           @Param("birthDate") Date birthDate,
                           @Param("location") Location location);
}
