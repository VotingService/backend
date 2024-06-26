package com.example.votingService.controller;

import com.example.votingService.util.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(BallotNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String answerOptionsNotFoundHandler(BallotNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CandidateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String authorNotFoundHandler(CandidateNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ElectionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String courseNotFoundHandler(ElectionNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserWithEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String userProgressExistForUserHandler(UserWithEmailException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String userProgressExistForUserHandler(WrongPasswordException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String moduleNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CanNotRetractVoteException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String questionNotFoundHandler(CanNotRetractVoteException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BadCandidatePointException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String questionNotFoundHandler(BadCandidatePointException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ExceededMaxVotesException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String questionNotFoundHandler(ExceededMaxVotesException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PluralityVoteException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String questionNotFoundHandler(PluralityVoteException ex) {
        return ex.getMessage();
    }
}
