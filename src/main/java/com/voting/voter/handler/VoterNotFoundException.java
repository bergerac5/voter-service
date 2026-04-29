package com.voting.voter.handler;

public class VoterNotFoundException extends RuntimeException {

    public VoterNotFoundException(String message) {
        super(message);
    }

}
