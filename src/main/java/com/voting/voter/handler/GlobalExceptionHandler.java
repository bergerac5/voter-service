package com.voting.voter.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.voting.voter.dtos.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Define exception handlers for VoterNotFoundException, validation errors, etc.
    @ExceptionHandler(VoterNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleVoterNotFound(VoterNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    /**
     * Catch-all exception handler
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneric(Exception ex) {
        ex.printStackTrace(); // Log the full stack trace for debugging
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An unexpected error occurred on voter service ",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

}
