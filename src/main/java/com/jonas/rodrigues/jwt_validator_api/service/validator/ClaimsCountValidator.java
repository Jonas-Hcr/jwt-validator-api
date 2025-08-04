package com.jonas.rodrigues.jwt_validator_api.service.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Map;

@Slf4j
public class ClaimsCountValidator implements JwtRuleValidator {

    private static final int EXPECTED_CLAIMS_COUNT = 3;
    private final Map<String, Object> claims;
    private String reason;

    public ClaimsCountValidator(Map<String, Object> claims) {
        this.claims = claims;
    }

    @Override
    public boolean isValid(JwtClaims claims) {
        String requestId = MDC.get("requestId");
        if (this.claims == null) {
            reason = "Claims não podem ser nulas";
            log.debug("Validação de quantidade de claims falhou: claims nulas [requestId={}]", requestId);
            return false;
        }

        int actualCount = this.claims.size();
        if (actualCount != EXPECTED_CLAIMS_COUNT) {
            reason = String.format("Quantidade de claims inválida. Esperado: %d, Atual: %d",
                EXPECTED_CLAIMS_COUNT, actualCount);
            log.debug("Validação de quantidade de claims falhou [requestId={}, expectedCount={}, actualCount={}]",
                requestId, EXPECTED_CLAIMS_COUNT, actualCount);
            return false;
        }

        log.debug("Validação de quantidade de claims bem-sucedida [requestId={}, claimsCount={}]",
            requestId, actualCount);
        return true;
    }

    @Override
    public String getReason() {
        return reason;
    }
}
