package com.example.votingService.util.exception;

public class PluralityVoteException extends RuntimeException {
    public PluralityVoteException() {
        super("You can not vote for more than one candidate");
    }
}
