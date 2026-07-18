package com.enviro.assessment.junior.lethabo.service;

import com.enviro.assessment.junior.lethabo.dto.LoginRequestDTO;
import com.enviro.assessment.junior.lethabo.entity.Investor;
import com.enviro.assessment.junior.lethabo.exception.BusinessRuleException;
import com.enviro.assessment.junior.lethabo.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.lethabo.repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final InvestorRepository investorRepository;

    public Map<String, Object> loginInvestor(LoginRequestDTO dto) {

        Investor investor = investorRepository.findByEmail(dto.getUsername())
                .or(() -> investorRepository.findByIdNumber(dto.getUsername()))
                .orElseThrow(() -> new ResourceNotFoundException("Investor not found"));

        if (!dto.getPassword().equals(investor.getPassword())) {
            throw new BusinessRuleException("Invalid credentials");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", investor.getId());
        response.put("username", investor.getEmail());
        response.put("fullName", investor.getFullName());
        response.put("role", "INVESTOR");
        response.put("message", "Login successful");

        return response;
    }

}