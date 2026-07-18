package com.enviro.assessment.junior.lethabo.service;

import com.enviro.assessment.junior.lethabo.dto.InvestorProfileDTO;
import com.enviro.assessment.junior.lethabo.dto.InvestorRequestDTO;
import com.enviro.assessment.junior.lethabo.dto.InvestorUpdateDTO;
import com.enviro.assessment.junior.lethabo.entity.Investor;
import com.enviro.assessment.junior.lethabo.exception.BusinessRuleException;
import com.enviro.assessment.junior.lethabo.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.lethabo.repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestorService {

    private final InvestorRepository investorRepository;
    private final Logger logger = LoggerFactory.getLogger(InvestorService.class);

    public Investor createInvestor(InvestorRequestDTO dto) {

        if (investorRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessRuleException("Email already exists");
        }

        Investor investor = new Investor();
        investor.setFullName(dto.getFullName());
        investor.setDateOfBirth(dto.getDateOfBirth());
        investor.setEmail(dto.getEmail());
        investor.setPhone(dto.getPhone());
        investor.setAddress(dto.getAddress());
        investor.setIdNumber(dto.getIdNumber());
        investor.setPassword(dto.getPassword());

        Investor saved = investorRepository.save(investor);

        logger.info("Investor created with ID: {}", saved.getId());
        return saved;
    }


    public List<Investor> getAllInvestors() {
        return investorRepository.findAll();
    }

    public Investor updateInvestor(Long id, InvestorUpdateDTO dto) {

        Investor investor = investorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investor not found"));

        investor.setFullName(dto.getFullName());
        investor.setPhone(dto.getPhone());
        investor.setAddress(dto.getAddress());

        return investorRepository.save(investor);
    }

    public void deleteInvestor(Long id) {
        if (!investorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Investor not found");
        }

        investorRepository.deleteById(id);
        logger.info("Investor deleted with ID: {}", id);
    }

    public InvestorProfileDTO getInvestorProfile(Long id) {

        Investor investor = investorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investor not found"));

        InvestorProfileDTO dto = new InvestorProfileDTO();

        dto.setId(investor.getId());
        dto.setFullName(investor.getFullName());
        dto.setEmail(investor.getEmail());
        dto.setPhone(investor.getPhone());
        dto.setAddress(investor.getAddress());
        dto.setIdNumber(investor.getIdNumber());
        dto.setDateOfBirth(investor.getDateOfBirth());

        return dto;
    }
}