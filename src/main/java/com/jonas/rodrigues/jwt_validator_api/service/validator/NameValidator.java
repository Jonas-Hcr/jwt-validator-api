package com.jonas.rodrigues.jwt_validator_api.service.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class NameValidator implements JwtRuleValidator {

    private String reason;

    @Override
    public boolean isValid(JwtClaims claims) {
        String requestId = MDC.get("requestId");
        String name = claims.name();

        if (nameIsNullOrEmpty(name)) {
            reason = "Nome não pode ser nulo ou vazio";
            log.debug("Validação de name falhou: name nulo ou vazio [requestId={}]", requestId);
            return false;
        }

        if (nameExceedsMaxLength(name)) {
            reason = "Nome excede o tamanho máximo de 256 caracteres";
            log.debug("Validação de name falhou: name excede o tamanho máximo [requestId={}, nameLength={}]",
                    requestId, name.length());
            return false;
        }

        if (nameContainsDigits(name)) {
            reason = "Nome não pode conter dígitos";
            log.debug("Validação de name falhou: name contém dígitos [requestId={}, name={}]",
                    requestId, name);
            return false;
        }

        log.debug("Validação de name bem-sucedida [requestId={}, name={}]",
                requestId, name);
        return true;
    }

    private boolean nameIsNullOrEmpty(String name) {
        return name == null || name.trim().isEmpty();
    }

    private boolean nameExceedsMaxLength(String name) {
        return name.length() > 256;
    }

    private boolean nameContainsDigits(String name) {
        return name.matches(".*\\d.*");
    }

    @Override
    public String getReason() {
        return reason;
    }
}
