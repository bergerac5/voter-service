package com.voting.voter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

@SpringBootApplication
@EnableKafka
public class VoterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoterServiceApplication.class, args);
		System.out.println("=== VOTER SERVICE STARTED ===");
	}

	/**
	 * Validator bean for validating incoming events
	 */
	@Bean
	public Validator validator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

}
