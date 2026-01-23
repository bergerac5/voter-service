package com.voting.voter.models;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity
public class Voter {

    @Id
    private UUID userId; // same as auth-service userId

    @Column(unique = true, nullable = false)
    @Size(max = 30)
    private String nationalId;
    @Column(nullable = false)
    @Size(max = 30)
    private String firstName;
    @Column(nullable = false)
    @Size(max = 30)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;

    private boolean eligible = true;
    private boolean hasVoted = false;

    private Instant registeredAt;
    private Instant lastUpdatedAt;

    private boolean active = true; // for soft deletes

    private String region; // e.g., district or province
    private String pollingStation; // optional, for routing

    public Voter() {
    }

    public Voter(UUID userId, String nationalId, String firstName, String lastName, boolean eligible, boolean hasVoted,
            Instant registeredAt, Instant lastUpdatedAt, boolean active, String region, String pollingStation,
            String username) {
        this.userId = userId;
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eligible = eligible;
        this.hasVoted = hasVoted;
        this.registeredAt = registeredAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.active = active;
        this.region = region;
        this.pollingStation = pollingStation;
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

    public boolean isEligible() {
        return this.eligible;
    }

    public boolean getEligible() {
        return this.eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public boolean isHasVoted() {
        return this.hasVoted;
    }

    public boolean getHasVoted() {
        return this.hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public Instant getRegisteredAt() {
        return this.registeredAt;
    }

    public void setRegisteredAt(Instant registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Instant getLastUpdatedAt() {
        return this.lastUpdatedAt;
    }

    public void setLastUpdatedAt(Instant lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPollingStation() {
        return this.pollingStation;
    }

    public void setPollingStation(String pollingStation) {
        this.pollingStation = pollingStation;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
