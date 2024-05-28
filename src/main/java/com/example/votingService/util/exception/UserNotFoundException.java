package com.example.votingService.util.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(Integer id) {
        super("Could not find 'User' with id=" + id);
    }


    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
