package com.voting.voter.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voting.voter.dtos.ApiResponse;
import com.voting.voter.dtos.VoterResponse;
import com.voting.voter.service.VoterService;

@RestController
@RequestMapping("/voters")
public class VoterController {

    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @GetMapping("/{voterId}")
    public ResponseEntity<ApiResponse<VoterResponse>> getVoterById(@PathVariable UUID voterId) {
        VoterResponse response = voterService.getVoterByUserId(voterId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Voter not found", HttpStatus.NOT_FOUND.value()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Voter retrieved successfully", response, HttpStatus.OK.value()));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<VoterResponse>>> getVotersByIds(@RequestBody List<UUID> ids) {

        List<VoterResponse> responses = voterService.getVotersByUserIds(ids);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Voters retrieved successfully", responses, HttpStatus.OK.value()));
    }

}
