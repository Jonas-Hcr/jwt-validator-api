package com.jonas.rodrigues.jwt_validator_api.service.validator;

import com.jonas.rodrigues.jwt_validator_api.entity.JwtClaims;

public class SeedValidator implements JwtRuleValidator {

    private static final String REASON = "Seed must be a valid prime number";

    @Override
    public boolean isValid(JwtClaims claims) {
        try {
            int seed = Integer.parseInt(claims.seed());
            return isPrime(seed);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isPrime(int number) {
        if (number < 2) return false;
        return java.util.stream.IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(i -> number % i == 0);
    }

    @Override
    public String getReason() {
        return REASON;
    }
}
