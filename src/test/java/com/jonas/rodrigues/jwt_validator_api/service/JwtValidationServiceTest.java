package com.jonas.rodrigues.jwt_validator_api.service;

import com.jonas.rodrigues.jwt_validator_api.dto.JwtRequestDTO;
import com.jonas.rodrigues.jwt_validator_api.dto.JwtResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtValidationServiceTest {

    private JwtValidationService service;

    @BeforeEach
    void setup() {
        service = new JwtValidationService();
    }

    @Test
    void shouldReturnTrueWhenTokenIsValid() {
        // Token válido com 3 claims: Name, Role, Seed
        String validToken = "eyJhbGciOiJIUzI1NiJ9." +
                "eyJOYW1lIjoiSm9uYXMiLCJSb2xlIjoiTWVtYmVyIiwiU2VlZCI6Ijk5NyJ9." +
                "Z2CQRlQuPrbgoHx6OF8pCJkn4PAr3FNKy-kVy-rRkJY";

        JwtRequestDTO request = new JwtRequestDTO(validToken);

        JwtResponseDTO response = service.validate(request);

        assertThat(response.valid()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenClaimsAreMissing() {
        // Token com apenas 2 claims
        String invalidToken = "eyJhbGciOiJIUzI1NiJ9." +
                "eyJOYW1lIjoiSm9uYXMiLCJSb2xlIjoiVVNFUiJ9." +
                "dNafI6LEaFcjcAl6vYi_cQkAzPXG0hPltKVG5OorCSc";

        JwtRequestDTO request = new JwtRequestDTO(invalidToken);

        JwtResponseDTO response = service.validate(request);

        assertThat(response.valid()).isFalse();
    }

    @Test
    void shouldReturnFalseWhenTokenIsMalformed() {
        String malformedToken = "this.is.not.valid";

        JwtRequestDTO request = new JwtRequestDTO(malformedToken);

        JwtResponseDTO response = service.validate(request);

        assertThat(response.valid()).isFalse();
    }

    @Test
    void shouldReturnFalseWhenRequiredClaimIsBlank() {
        // Seed vazio
        String tokenWithEmptyClaim = "eyJhbGciOiJIUzI1NiJ9." +
                "eyJOYW1lIjoiSm9uYXMiLCJSb2xlIjoiVVNFUiIsIlNlZWQiOiIi" + // Seed vazio
                "}." +
                "sBXLcfE9C_sAqlpFeS-pF4g3-fWEGy4TtK_U3mV7Uvo";

        JwtRequestDTO request = new JwtRequestDTO(tokenWithEmptyClaim);

        JwtResponseDTO response = service.validate(request);

        assertThat(response.valid()).isFalse();
    }
}
