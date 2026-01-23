package com.voting.voter.events;

import java.time.Instant;
import java.util.function.Consumer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.online.voting.events.UserCreatedEvent;
import com.online.voting.events.UserDeletedEvent;
import com.online.voting.events.UserUpdatedEvent;
import com.online.voting.events.VoterCreationFailedEvent;
import com.online.voting.events.VoterCreationSucceededEvent;
import com.online.voting.events.VoterUpdateFailedEvent;
import com.online.voting.events.VoterUpdateSucceededEvent;
import com.voting.voter.models.Voter;
import com.voting.voter.repository.VoterRepository;

@Configuration
public class UserEventConsumer {

    private final VoterRepository voterRepository;
    private final StreamBridge streamBridge;

    public UserEventConsumer(VoterRepository voterRepository, StreamBridge streamBridge) {
        this.voterRepository = voterRepository;
        this.streamBridge = streamBridge;
        System.out.println("✅ UserEventConsumer bean created");
    }

    // ---------------- USER CREATED ----------------
    @Bean
    public Consumer<UserCreatedEvent> userCreated() {
        return event -> {
            System.out.println("📥 Received UserCreatedEvent: " + event);

            // Only handle VOTER role
            if (!"VOTER".equalsIgnoreCase(event.getRole())) {
                System.out.println("[VOTER] Skipping non-VOTER role: " + event.getRole());
                return;
            }

            try {
                // Uniqueness checks
                if (voterRepository.existsByUserId(event.getUserId())) {
                    throw new IllegalStateException("Duplicate userId");
                }
                if (voterRepository.existsByNationalId(event.getNationalId())) {
                    throw new IllegalStateException("Duplicate nationalId");
                }

                // Create voter entity
                Voter voter = new Voter();
                voter.setUserId(event.getUserId());
                voter.setUsername(event.getUsername());
                voter.setNationalId(event.getNationalId());
                voter.setFirstName(event.getFirstName());
                voter.setLastName(event.getLastName());
                voter.setRegisteredAt(Instant.now());
                voter.setActive(true);

                // Save voter
                voterRepository.save(voter);
                System.out.println("[VOTER] Created voter: " + voter.getUserId());

                // Publish success event
                VoterCreationSucceededEvent succeeded = new VoterCreationSucceededEvent(
                        event.getUserId(),
                        event.getNationalId());
                streamBridge.send("voterCreationSucceeded-out-0", succeeded);

            } catch (Exception e) {
                System.err.println("[VOTER] Failed to create voter: " + e.getMessage());

                // Publish failure event
                VoterCreationFailedEvent failed = new VoterCreationFailedEvent(
                        event.getUserId(),
                        event.getNationalId(),
                        e.getMessage());
                streamBridge.send("voterCreationFailed-out-0", failed);
            }
        };
    }

    // ---------------- USER UPDATED ----------------
    @Bean
    public Consumer<UserUpdatedEvent> userUpdated() {
        return event -> {
            System.out.println("📥 Received UserUpdatedEvent: " + event);

            if (!"VOTER".equalsIgnoreCase(event.getRole())) {
                System.out.println("[VOTER] Skipping non-VOTER role: " + event.getRole());
                return;
            }

            try {
                Voter voter = voterRepository.findByUserId(event.getUserId());
                if (voter == null) {
                    throw new IllegalStateException("Voter not found");
                }

                // National ID uniqueness check (safe)
                if (!event.getNationalId().equals(voter.getNationalId())
                        && voterRepository.existsByNationalId(event.getNationalId())) {
                    throw new IllegalStateException("Duplicate nationalId");
                }

                voter.setUsername(event.getUsername());
                voter.setNationalId(event.getNationalId());
                voter.setFirstName(event.getFirstName());
                voter.setLastName(event.getLastName());
                voter.setLastUpdatedAt(Instant.now());

                voterRepository.save(voter);

                VoterUpdateSucceededEvent succeeded = new VoterUpdateSucceededEvent(
                        event.getUserId());
                System.out.println("============= UserUpdateSucceededEvent ===============");

                streamBridge.send("voterUpdateSucceeded-out-0", succeeded);

                System.out.println("[VOTER] Voter updated successfully for userId=" + event.getUserId());

            } catch (Exception e) {
                System.err.println("[VOTER] Failed to update voter: " + e.getMessage());
                System.out.println("============= UserUpdateFailedEvent===============");

                VoterUpdateFailedEvent failed = new VoterUpdateFailedEvent(
                        event.getUserId(),
                        e.getMessage());

                streamBridge.send("voterUpdateFailed-out-0", failed);
            }
        };
    }

    // ---------- USER DELETED ----------
    @Bean
    public Consumer<UserDeletedEvent> userDeleted() {
        return event -> {
            System.out.println("📥 Received UserDeletedEvent: " + event);

            try {
                Voter voter = voterRepository.findByUserId(event.getUserId());
                if (voter == null) {
                    System.out.println("[VOTER] Voter not found for userId=" + event.getUserId());
                    return;
                }
                ;

                voterRepository.delete(voter);
                System.out.println("[VOTER] Deleted voter: " + voter.getUserId());
            } catch (Exception e) {
                System.err.println("[VOTER] Failed to delete voter: " + e.getMessage());
            }
        };
    };

}