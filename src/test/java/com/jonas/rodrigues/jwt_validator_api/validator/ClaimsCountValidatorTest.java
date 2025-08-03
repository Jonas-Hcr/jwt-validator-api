package com.jonas.rodrigues.jwt_validator_api.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;
import com.jonas.rodrigues.jwt_validator_api.service.validator.ClaimsCountValidator;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ClaimsCountValidatorTest {

    @Test
    void shouldReturnTrueWhenExactlyThreeClaims() {
        Map<String, Object> claimsMap = Map.of(
            "Name", "João",
            "Role", "Admin",
            "Seed", "7"
        );

        JwtClaims claims = new JwtClaims("João", "Admin", "7");
        ClaimsCountValidator validator = new ClaimsCountValidator(claimsMap);

        assertTrue(validator.isValid(claims));
    }

    @Test
    void shouldReturnFalseWhenMoreThanThreeClaims() {
        Map<String, Object> claimsMap = Map.of(
            "Name", "João",
            "Role", "Admin",
            "Seed", "7",
            "Extra", "X"
        );

        JwtClaims claims = new JwtClaims("João", "Admin", "7");
        ClaimsCountValidator validator = new ClaimsCountValidator(claimsMap);

        assertFalse(validator.isValid(claims));
    }

    @Test
    void shouldReturnFalseWhenLessThanThreeClaims() {
        Map<String, Object> claimsMap = Map.of(
            "Name", "João",
            "Role", "Admin"
        );

        JwtClaims claims = new JwtClaims("João", "Admin", "7");
        ClaimsCountValidator validator = new ClaimsCountValidator(claimsMap);

        assertFalse(validator.isValid(claims));
    }
}