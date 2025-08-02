package com.jonas.rodrigues.jwt_validator_api.service.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;

public class NameValidator implements JwtRuleValidator {

    private static final String REASON = "Name must be <= 256 characters and contain no digits";

    @Override
    public boolean isValid(JwtClaims claims) {
        String name = claims.name();
        return !nameIsNullOrEmpty(name) &&
               !nameExceedsMaxLength(name) &&
               !nameContainsDigits(name);
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
        return REASON;
    }
}
