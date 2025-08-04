package com.jonas.rodrigues.jwt_validator_api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jonas.rodrigues.jwt_validator_api.dto.JwtRequestDTO;
import com.jonas.rodrigues.jwt_validator_api.dto.JwtResponseDTO;
import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;
import com.jonas.rodrigues.jwt_validator_api.service.validator.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtValidationService {

    public JwtResponseDTO validate(JwtRequestDTO request) {
        String requestId = MDC.get("requestId");
        try {
            log.info("Iniciando decodificação do JWT [requestId={}]", requestId);
            DecodedJWT decoded = decodeToken(request.token());
            Map<String, Object> claimsMap = decoded.getClaims().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            e -> e.getValue().as(Object.class)));

            JwtClaims claims = new JwtClaims(
                decoded.getClaim("Role").asString(),
                decoded.getClaim("Seed").asString(),
                decoded.getClaim("Name").asString()
            );

            log.debug("Claims extraídas do JWT [requestId={}, claims={}]", requestId, claims);

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
                    if (!isValid) {
                        String validatorName = validator.getClass().getSimpleName();
                        String reason = validator.getReason();
                        reasons.add(reason);
                        log.warn("Falha na validação [requestId={}, validator={}, reason={}]",
                                requestId, validatorName, reason);
                    }
                    return isValid;
                });

            if (!allValid) {
                log.error("JWT inválido [requestId={}, failures={}]", requestId, reasons);
            } else {
                log.info("JWT válido [requestId={}]", requestId);
            }

            return new JwtResponseDTO(allValid);
        } catch (Exception e) {
            log.error("Erro ao processar JWT [requestId={}, error={}, stackTrace={}]",
                    requestId, e.getMessage(), e.getStackTrace());
            return new JwtResponseDTO(false);
        }
    }

    private DecodedJWT decodeToken(String token) {
        try {
            String requestId = MDC.get("requestId");
            log.debug("Decodificando token [requestId={}]", requestId);
            DecodedJWT jwt = JWT.decode(token);
            log.debug("Token decodificado com sucesso [requestId={}]", requestId);
            return jwt;
        } catch (JWTDecodeException e) {
            String requestId = MDC.get("requestId");
            log.error("Falha ao decodificar JWT [requestId={}, error={}]", requestId, e.getMessage());
            throw e;
        }
    }
}
