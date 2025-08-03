package com.jonas.rodrigues.jwt_validator_api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jonas.rodrigues.jwt_validator_api.dto.JwtRequestDTO;
import com.jonas.rodrigues.jwt_validator_api.dto.JwtResponseDTO;
import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;
import com.jonas.rodrigues.jwt_validator_api.service.validator.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtValidationService {

    public JwtResponseDTO validate(JwtRequestDTO request) {
        try {
            log.info("Validating JWT: {}", request.token());
            DecodedJWT decoded = decodeToken(request.token());
            Map<String, Object> claimsMap = decoded.getClaims().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            e -> e.getValue().as(Object.class)));

            JwtClaims claims = new JwtClaims(
                decoded.getClaim("Role").asString(),
                decoded.getClaim("Seed").asString(),
                decoded.getClaim("Name").asString()
            );

            log.info("Decoded JWT claims: {}", claims);

            List<JwtRuleValidator> validators = List.of(
                new ClaimsCountValidator(claimsMap),
                new NameValidator(),
                new RoleValidator(),
                new SeedValidator()
            );

            List<String> reasons = new ArrayList<>();
            boolean allValid = validators.stream()
                .allMatch(validator -> {
                    boolean isValid = validator.isValid(claims);
                    if (!isValid) reasons.add(validator.getReason());
                    return isValid;
                });

            if (!allValid) {
                log.warn("JWT validation failed: {}", reasons);
            } else {
                log.info("JWT validation succeeded");
            }

            return new JwtResponseDTO(allValid);
        } catch (Exception e) {
            log.error("Error validating JWT: {}", e.getMessage());
            return new JwtResponseDTO(false);
        }
    }

    private DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }
}
