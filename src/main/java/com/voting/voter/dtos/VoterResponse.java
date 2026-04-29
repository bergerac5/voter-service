package com.voting.voter.dtos;

import java.util.UUID;

public class VoterResponse {

    private UUID userId;

    private String nationalId;
    private String firstName;
    private String lastName;

    private String username;

    public VoterResponse() {
    }

    public VoterResponse(UUID userId, String nationalId, String firstName, String lastName, String username) {
        this.userId = userId;
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getNationalId() {
        return this.nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
