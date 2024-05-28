package com.example.votingService.util.exception;

public class CanNotRetractVoteException extends RuntimeException {
    public CanNotRetractVoteException(Integer id) {
        super("You can not retract votes in this election with id=" + id);
    }
}
