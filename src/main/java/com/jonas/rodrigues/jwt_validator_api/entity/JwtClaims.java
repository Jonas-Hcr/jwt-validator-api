package com.jonas.rodrigues.jwt_validator_api.entity;

public record JwtClaims(
    String role,
    String seed,
    String name
) {}
