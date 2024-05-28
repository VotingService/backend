package com.example.votingService.util.exception;

public class ElectionNotFoundException extends RuntimeException {
    public ElectionNotFoundException(Integer id) {
        super("Could not find 'Election' with id=" + id);
    }
}