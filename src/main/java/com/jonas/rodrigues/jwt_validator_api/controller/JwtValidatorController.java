package com.jonas.rodrigues.jwt_validator_api.controller;

import com.jonas.rodrigues.jwt_validator_api.dto.JwtRequestDTO;
import com.jonas.rodrigues.jwt_validator_api.dto.JwtResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jwt-validator")
@Slf4j
public class JwtValidatorController {

    @PostMapping
    public ResponseEntity<JwtResponseDTO> validateJwt(@RequestBody JwtRequestDTO jwtRequest) {
        log.info("Received JWT for validation: {}", jwtRequest.token());

        JwtResponseDTO response = new JwtResponseDTO(true);

        return ResponseEntity.ok(response);
    }
}
