package com.jonas.rodrigues.jwt_validator_api.service.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class SeedValidator implements JwtRuleValidator {

    public String reason;

    @Override
    public boolean isValid(JwtClaims claims) {
        String requestId = MDC.get("requestId");

        if (claims == null || claims.seed() == null || claims.seed().isBlank()) {
            reason = "Seed não pode ser nula ou vazia";
            log.debug("Validação de seed falhou: seed nula ou vazia [requestId={}]", requestId);
            return false;
        }

        if (!claims.seed().matches("\\d+")) {
            reason = "Seed deve ser um número inteiro";
            log.debug("Validação de seed falhou: seed não é um número inteiro [requestId={}, seed={}]", requestId, claims.seed());
            return false;
        }

        if (!isPrime(Integer.parseInt(claims.seed()))) {
            reason = "Seed deve ser um número primo";
            log.debug("Validação de seed falhou: seed não é primo [requestId={}, seed={}]", requestId, claims.seed());
            return false;
        }

        log.debug("Validação de seed bem-sucedida [requestId={}, seed={}]", requestId, claims.seed());
        return true;
    }

    private boolean isPrime(int number) {
        if (number < 2) return false;
        return java.util.stream.IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(i -> number % i == 0);
    }

    @Override
    public String getReason() {
        return reason;
    }
}
