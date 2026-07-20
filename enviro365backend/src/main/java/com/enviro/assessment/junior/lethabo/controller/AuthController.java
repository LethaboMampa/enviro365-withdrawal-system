package com.enviro.assessment.junior.lethabo.controller;

import com.enviro.assessment.junior.lethabo.dto.LoginRequestDTO;
import com.enviro.assessment.junior.lethabo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/investor")
    public ResponseEntity<Map<String, Object>> loginInvestor(
            @Valid @RequestBody LoginRequestDTO dto) {

        return ResponseEntity.ok(authService.loginInvestor(dto));
    }
}