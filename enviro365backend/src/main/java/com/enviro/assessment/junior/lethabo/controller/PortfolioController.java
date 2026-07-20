package com.enviro.assessment.junior.lethabo.controller;

import com.enviro.assessment.junior.lethabo.dto.PortfolioRequestDTO;
import com.enviro.assessment.junior.lethabo.entity.Portfolio;
import com.enviro.assessment.junior.lethabo.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(
            @Valid @RequestBody PortfolioRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(portfolioService.createPortfolio(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolio(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                portfolioService.getPortfolio(id));
    }

    @GetMapping("/investor/{investorId}")
    public ResponseEntity<List<Portfolio>> getByInvestor(
            @PathVariable Long investorId) {

        return ResponseEntity.ok(
                portfolioService.getPortfoliosByInvestor(investorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(
            @PathVariable Long id) {

        portfolioService.deletePortfolio(id);

        return ResponseEntity.noContent().build();
    }
}