package com.enviro.assessment.junior.lethabo.controller;

import com.enviro.assessment.junior.lethabo.dto.InvestorProfileDTO;
import com.enviro.assessment.junior.lethabo.dto.InvestorRequestDTO;
import com.enviro.assessment.junior.lethabo.dto.InvestorUpdateDTO;
import com.enviro.assessment.junior.lethabo.entity.Investor;
import com.enviro.assessment.junior.lethabo.service.InvestorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investors")
@RequiredArgsConstructor
public class InvestorController {

    private final InvestorService investorService;

    @PostMapping
    public ResponseEntity<Investor> createInvestor(
            @Valid @RequestBody InvestorRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(investorService.createInvestor(dto));
    }

    @GetMapping
    public ResponseEntity<List<Investor>> getAllInvestors() {
        return ResponseEntity.ok(investorService.getAllInvestors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestorProfileDTO> getInvestor(@PathVariable Long id) {
        return ResponseEntity.ok(investorService.getInvestorProfile(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Investor> updateInvestor(
            @PathVariable Long id,
            @RequestBody InvestorUpdateDTO dto) {

        Investor investor = investorService.updateInvestor(id, dto);
        return ResponseEntity.ok(investor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestor(@PathVariable Long id) {

        investorService.deleteInvestor(id);

        return ResponseEntity.noContent().build();
    }
}