package com.example.votingService.util.exception;

public class UserWithEmailException extends RuntimeException {
    public UserWithEmailException(String email) {
        super("User with email(" + email + ") already exists");
    }
}

