package com.jonas.rodrigues.jwt_validator_api.validator;


import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;
import com.jonas.rodrigues.jwt_validator_api.service.validator.NameValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameValidatorTest {

    private final NameValidator validator = new NameValidator();

    @Test
    void shouldReturnTrueForValidName() {
        JwtClaims claims = new JwtClaims("Admin", "123", "Toninho Araujo");
        assertTrue(validator.isValid(claims));
    }

    @Test
    void shouldReturnFalseForNameWithNumbers() {
        JwtClaims claims = new JwtClaims("Member", "123", "M4ria Olivia");
        assertFalse(validator.isValid(claims));
    }

    @Test
    void shouldReturnFalseForNameTooLong() {
        String longName = "A".repeat(257);
        JwtClaims claims = new JwtClaims("Member", "123", longName);
        assertFalse(validator.isValid(claims));
    }

    @Test
    void shouldReturnFalseForNullName() {
        JwtClaims claims = new JwtClaims("Member", "123", null);
        assertFalse(validator.isValid(claims));
    }
}

