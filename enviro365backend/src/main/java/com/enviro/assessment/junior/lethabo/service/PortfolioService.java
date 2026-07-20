package com.enviro.assessment.junior.lethabo.service;

import com.enviro.assessment.junior.lethabo.dto.PortfolioRequestDTO;
import com.enviro.assessment.junior.lethabo.entity.Investor;
import com.enviro.assessment.junior.lethabo.entity.Portfolio;
import com.enviro.assessment.junior.lethabo.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.lethabo.repository.InvestorRepository;
import com.enviro.assessment.junior.lethabo.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final InvestorRepository investorRepository;
    private final Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    @Transactional
    public Portfolio createPortfolio(PortfolioRequestDTO dto) {

        Investor investor = investorRepository.findById(dto.getInvestorId())
                .orElseThrow(() -> new ResourceNotFoundException("Investor not found"));

        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioName(dto.getPortfolioName());
        portfolio.setBalance(dto.getBalance());
        portfolio.setPortfolioType(dto.getPortfolioType());
        portfolio.setInvestor(investor);

        Portfolio saved = portfolioRepository.save(portfolio);

        logger.info("Portfolio created with ID: {}", saved.getId());
        return saved;
    }

    public List<Portfolio> getPortfoliosByInvestor(Long investorId) {
        return portfolioRepository.findByInvestorId(investorId);
    }

    public Portfolio getPortfolio(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
    }

    @Transactional
    public void updatePortfolioBalance(Long id, Double newBalance) {

        Portfolio portfolio = getPortfolio(id);
        portfolio.setBalance(newBalance);

        portfolioRepository.save(portfolio);

        logger.info("Portfolio balance updated with ID: {}", id);
    }

    public void deletePortfolio(Long id) {

        if (!portfolioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Portfolio not found");
        }

        portfolioRepository.deleteById(id);
        logger.info("Portfolio deleted with ID: {}", id);
    }
}