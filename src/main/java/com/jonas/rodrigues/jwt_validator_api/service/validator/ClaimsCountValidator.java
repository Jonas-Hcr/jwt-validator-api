package com.jonas.rodrigues.jwt_validator_api.service.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;

import java.util.Map;

public class ClaimsCountValidator implements JwtRuleValidator {
    private final Map<String, Object> claimsMap;
    private static final String REASON = "JWT must contain exactly 3 claims.";

    public ClaimsCountValidator(Map<String, Object> claimsMap) {
        this.claimsMap = claimsMap;
    }

    @Override
    public boolean isValid(JwtClaims claims) {
        return claimsMap.size() == 3;
    }

    @Override
    public String getReason() {
        return REASON;
    }
}
