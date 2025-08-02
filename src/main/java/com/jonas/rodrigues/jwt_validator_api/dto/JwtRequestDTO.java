package com.jonas.rodrigues.jwt_validator_api.dto;
import jakarta.validation.constraints.NotBlank;


public record JwtRequestDTO(
    @NotBlank(message = "Token JWT is required")
    String token
) {}
