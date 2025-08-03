package com.jonas.rodrigues.jwt_validator_api.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;
import com.jonas.rodrigues.jwt_validator_api.service.validator.SeedValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeedValidatorTest {

    private final SeedValidator validator = new SeedValidator();

    @Test
    void shouldReturnTrueForPrimeSeed() {
        JwtClaims claims = new JwtClaims("Admin", "7", "Maria");
        assertTrue(validator.isValid(claims));
    }

    @Test
    void shouldReturnFalseForNonPrimeSeed() {
        JwtClaims claims = new JwtClaims("Admin", "10", "Maria");
        assertFalse(validator.isValid(claims));
    }

    @Test
    void shouldReturnFalseForNonNumericSeed() {
        JwtClaims claims = new JwtClaims("Admin", "abc", "Maria");
        assertFalse(validator.isValid(claims));
    }

    @Test
    void shouldReturnFalseForNullSeed() {
        JwtClaims claims = new JwtClaims("Admin", null, "Maria");
        assertFalse(validator.isValid(claims));
    }
}