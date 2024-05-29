package com.example.votingService.util.exception;

public class ExceededMaxVotesException extends RuntimeException {
    public ExceededMaxVotesException() {
        super("You exceeded max votes limit");
    }
}