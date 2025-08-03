package com.jonas.rodrigues.jwt_validator_api.service.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;

import java.util.Set;

public class RoleValidator implements JwtRuleValidator {

    private static final Set<String> VALID_ROLES = Set.of("Admin", "Member", "External");
    private static final String REASON = "Role must be one of: Admin, Member, External.";

    @Override
    public boolean isValid(JwtClaims claims) {
        if (claims == null || claims.role() == null) {
            return false;
        }
        return VALID_ROLES.contains(claims.role());
    }

    @Override
    public String getReason() {
        return REASON;
    }

}
