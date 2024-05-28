package com.example.votingService.util.exception;

public class BadCandidatePointException extends RuntimeException {
    public BadCandidatePointException() {
        super("Can not be such point for candidate");
    }
}
