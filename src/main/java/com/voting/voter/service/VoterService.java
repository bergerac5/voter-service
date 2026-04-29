package com.voting.voter.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.voting.voter.dtos.VoterResponse;
import com.voting.voter.handler.VoterNotFoundException;
import com.voting.voter.models.Voter;
import com.voting.voter.repository.VoterRepository;

@Service
public class VoterService {

    private final VoterRepository voterRepository;

    public VoterService(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    // findvoter by user id
    public VoterResponse getVoterByUserId(UUID userId) {
        if (userId == null) {
            throw new ResourceNotFoundException("User ID cannot be null");
        }
        Voter voter = voterRepository.findByUserId(userId);
        if (voter == null) {
            throw new VoterNotFoundException("Voter not found with userId: " + userId);
        }
        return mapToVoterResponse(voter);

    }

    // get voters by list of user ids
    public List<VoterResponse> getVotersByUserIds(List<UUID> ids) {
        if (ids == null || ids.isEmpty())
            return List.of();

        List<Voter> voters = voterRepository.findAllById(ids);
        return voters.stream().map(this::mapToVoterResponse).collect(Collectors.toList());
    }

    private VoterResponse mapToVoterResponse(Voter voter) {
        return new VoterResponse(
                voter.getUserId(),
                voter.getNationalId(),
                voter.getFirstName(),
                voter.getLastName(),
                voter.getUsername());
    }

}
