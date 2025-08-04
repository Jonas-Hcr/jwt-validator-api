package com.jonas.rodrigues.jwt_validator_api.service.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Set;

@Slf4j
public class RoleValidator implements JwtRuleValidator {

    private static final Set<String> VALID_ROLES = Set.of("Admin", "Member", "External");
    private String reason;

    @Override
    public boolean isValid(JwtClaims claims) {
        String requestId = MDC.get("requestId");
        if (claims == null || claims.role() == null || claims.role().isBlank()) {
            reason = "Role não pode ser nula ou vazia";
            log.debug("Validação de role falhou: role nula ou vazia [requestId={}]", requestId);
            return false;
        }

        boolean isValid = VALID_ROLES.contains(claims.role());
        if (!isValid) {
            reason = String.format("Role '%s' inválida. Roles permitidas: %s",
                claims.role(), String.join(", ", VALID_ROLES));
            log.debug("Validação de role falhou: role inválida [requestId={}, role={}]",
                requestId, claims.role());
        } else {
            log.debug("Validação de role bem-sucedida [requestId={}, role={}]",
                requestId, claims.role());
        }
        return isValid;
    }

    @Override
    public String getReason() {
        return reason;
    }

}
