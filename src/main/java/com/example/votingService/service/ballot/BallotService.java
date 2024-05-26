package com.example.votingService.service.ballot;

import com.example.votingService.domain.ballot.Ballot;
import com.example.votingService.domain.request.ChangePasswordRequest;
import com.example.votingService.domain.user.Role;
import com.example.votingService.domain.user.User;
import com.example.votingService.repository.ballot.BallotRepository;
import com.example.votingService.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BallotService {
    @Autowired
    private final BallotRepository repository;

    public List<Ballot> findAllBallots() {
        return repository.findAll();
    }

    public Ballot findBallotById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    public Ballot createBallot(Ballot ballot) {
        return repository.save(ballot);
    }

    public Ballot updateBallot(Ballot ballot) {
        return repository.save(ballot);
    }

    public void deleteBallotById(Integer id) {
        repository.deleteById(id);
    }

    public List<Ballot> getAllBallotsByVoter(Integer id){
        return repository.getAllBallotsByVoterId(id);
    }
}

