package com.jonas.rodrigues.jwt_validator_api.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;
import com.jonas.rodrigues.jwt_validator_api.service.validator.RoleValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleValidatorTest {

    private final RoleValidator validator = new RoleValidator();

    @Test
    void shouldReturnTrueForValidRole() {
        JwtClaims claims = new JwtClaims("Admin", "123", "Maria");
        assertTrue(validator.isValid(claims));
    }

    @Test
    void shouldReturnFalseForInvalidRole() {
        JwtClaims claims = new JwtClaims("Root", "123", "Maria");
        assertFalse(validator.isValid(claims));
    }

    @Test
    void shouldReturnFalseForNullRole() {
        JwtClaims claims = new JwtClaims(null, "123", "Maria");
        assertFalse(validator.isValid(claims));
    }
}
