package com.example.votingService.util.exception;

public class BallotNotFoundException extends RuntimeException {
    public BallotNotFoundException(Integer id) {
        super("Could not find 'Ballot' with id=" + id);
    }
}
