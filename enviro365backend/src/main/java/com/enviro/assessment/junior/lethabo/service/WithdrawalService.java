package com.enviro.assessment.junior.lethabo.service;

import com.enviro.assessment.junior.lethabo.dto.ReportFilterDTO;
import com.enviro.assessment.junior.lethabo.dto.WithdrawalReportDTO;
import com.enviro.assessment.junior.lethabo.dto.WithdrawalRequestDTO;
import com.enviro.assessment.junior.lethabo.entity.Investor;
import com.enviro.assessment.junior.lethabo.entity.Portfolio;
import com.enviro.assessment.junior.lethabo.entity.Withdrawal;
import com.enviro.assessment.junior.lethabo.exception.BusinessRuleException;
import com.enviro.assessment.junior.lethabo.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.lethabo.repository.PortfolioRepository;
import com.enviro.assessment.junior.lethabo.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawalService {

    private final PortfolioRepository portfolioRepository;
    private final WithdrawalRepository withdrawalRepository;

    @Transactional
    public Withdrawal submitWithdrawal(WithdrawalRequestDTO request) {

        Portfolio portfolio = portfolioRepository.findById(request.getPortfolioId())
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));

        Investor investor = portfolio.getInvestor();

        double amount = request.getAmount();
        double balance = portfolio.getBalance();

        if (investor == null) {
            throw new BusinessRuleException("Investor not found");
        }

        if (investor.getDateOfBirth() == null) {
            throw new BusinessRuleException(
                    "Investor date of birth is missing");
        }

        int age = Period.between(
                investor.getDateOfBirth(),
                LocalDate.now()
        ).getYears();

        // RULE 1: Age restriction
        if (portfolio.getPortfolioType() == Portfolio.PortfolioType.RETIREMENT
                && age <= 65) {

            throw new BusinessRuleException(
                    "Retirement withdrawals only allowed for investors older than 65");
        }

        // RULE 2: Cannot exceed balance
        if (amount > balance) {
            throw new BusinessRuleException("Amount exceeds balance");
        }

        // RULE 3: Cannot exceed 90%
        if (amount > balance * 0.9) {
            throw new BusinessRuleException("Cannot withdraw more than 90% of balance");
        }

        Withdrawal withdrawal = Withdrawal.builder()
                .amount(amount)
                .reason(request.getReason())
                .status(Withdrawal.WithdrawalStatus.PENDING)
                .portfolio(portfolio)
                .build();

        portfolio.setBalance(balance - amount);
        portfolioRepository.save(portfolio);

        return withdrawalRepository.save(withdrawal);
    }

    public List<Withdrawal> getWithdrawalHistory(Long portfolioId) {
        return withdrawalRepository.findByPortfolioId(portfolioId);
    }

    public List<Withdrawal> getAllWithdrawals() {
        return withdrawalRepository.findAll();
    }

    public List<Withdrawal> getInvestorWithdrawals(Long investorId) {
        return withdrawalRepository.findByPortfolio_Investor_Id(investorId);
    }

    public List<WithdrawalReportDTO> generateReport(ReportFilterDTO filter) {

        List<Withdrawal> withdrawals = withdrawalRepository.findAll();

        return withdrawals.stream()
                .filter(w -> {

                    // DATE FILTER
                    if (filter.getFromDate() != null &&
                            w.getWithdrawalDate().toLocalDate().isBefore(filter.getFromDate())) {
                        return false;
                    }

                    if (filter.getToDate() != null &&
                            w.getWithdrawalDate().toLocalDate().isAfter(filter.getToDate())) {
                        return false;
                    }

                    // STATUS FILTER
                    if (filter.getStatus() != null &&
                            !w.getStatus().name().equalsIgnoreCase(filter.getStatus())) {
                        return false;
                    }

                    // AMOUNT FILTER
                    if (filter.getAmount() != null) {

                        switch (filter.getAmountFilterType()) {
                            case "GT":
                                if (!(w.getAmount() > filter.getAmount())) return false;
                                break;
                            case "LT":
                                if (!(w.getAmount() < filter.getAmount())) return false;
                                break;
                            case "EQ":
                                if (!(w.getAmount().equals(filter.getAmount()))) return false;
                                break;
                        }
                    }

                    return true;
                })
                .map(w -> {

                    Portfolio p = w.getPortfolio();
                    Investor i = p.getInvestor();

                    WithdrawalReportDTO dto = new WithdrawalReportDTO();

                    // investor
                    dto.setInvestorId(i.getId());
                    dto.setInvestorName(i.getFullName());
                    dto.setInvestorEmail(i.getEmail());

                    // portfolio
                    dto.setPortfolioId(p.getId());
                    dto.setPortfolioName(p.getPortfolioName());
                    dto.setPortfolioType(p.getPortfolioType().name());
                    dto.setPortfolioBalance(p.getBalance());

                    // withdrawal
                    dto.setWithdrawalId(w.getId());
                    dto.setAmount(w.getAmount());
                    dto.setReason(w.getReason());
                    dto.setStatus(w.getStatus().name());
                    dto.setWithdrawalDate(w.getWithdrawalDate());

                    // products
                    StringBuilder productText = new StringBuilder();

                    if (p.getProducts() != null) {
                        for (var prod : p.getProducts()) {
                            productText.append(prod.getProductName())
                                    .append(" (")
                                    .append(prod.getUnits())
                                    .append(" x ")
                                    .append(prod.getUnitPrice())
                                    .append("), ");
                        }
                    }

                    dto.setProducts(productText.toString());

                    return dto;
                })
                .toList();
    }
}