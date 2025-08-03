package com.jonas.rodrigues.jwt_validator_api.service.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;

public interface JwtRuleValidator {
    boolean isValid(JwtClaims claims);
    String getReason();
}
