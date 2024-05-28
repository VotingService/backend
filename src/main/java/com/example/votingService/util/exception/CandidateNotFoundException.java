package com.example.votingService.util.exception;

public class CandidateNotFoundException extends RuntimeException {
    public CandidateNotFoundException(Integer id) {
        super("Could not find 'Candidate' with id=" + id);
    }
}
