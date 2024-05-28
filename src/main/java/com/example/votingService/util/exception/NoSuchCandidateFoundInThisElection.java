package com.example.votingService.util.exception;

public class NoSuchCandidateFoundInThisElection extends RuntimeException {
    public NoSuchCandidateFoundInThisElection(Integer candidateId, Integer electionId) {
        super("Could not find 'Candidate' with id=" + candidateId + " in 'Election' with id=" + electionId);
    }
}
