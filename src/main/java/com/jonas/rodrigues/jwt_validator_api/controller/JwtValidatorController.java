package com.jonas.rodrigues.jwt_validator_api.controller;

import com.jonas.rodrigues.jwt_validator_api.dto.JwtRequestDTO;
import com.jonas.rodrigues.jwt_validator_api.dto.JwtResponseDTO;
import com.jonas.rodrigues.jwt_validator_api.service.JwtValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.MDC;

@RestController
@RequestMapping("/api/v1/jwt-validator")
@Slf4j
public class JwtValidatorController {

    @Autowired
    private JwtValidationService jwtValidationService;

    @PostMapping
    public ResponseEntity<JwtResponseDTO> validateJwt(@RequestBody JwtRequestDTO jwtRequest) {
        String requestId = java.util.UUID.randomUUID().toString();
        MDC.put("requestId", requestId);
        log.info("Iniciando validação de JWT [requestId={}]", requestId);

        try {
            JwtResponseDTO response = jwtValidationService.validate(jwtRequest);
            log.info("Validação de JWT concluída [requestId={}, status={}]",
                    requestId, response.valid() ? "válido" : "inválido");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro inesperado na validação do JWT [requestId={}, error={}]",
                    requestId, e.getMessage(), e);
            throw e;
        } finally {
            MDC.remove("requestId");
        }
    }
}
