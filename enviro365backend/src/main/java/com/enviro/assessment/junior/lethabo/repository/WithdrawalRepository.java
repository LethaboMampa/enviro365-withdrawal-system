package com.enviro.assessment.junior.lethabo.repository;

import com.enviro.assessment.junior.lethabo.entity.Withdrawal;
import com.enviro.assessment.junior.lethabo.entity.Withdrawal.WithdrawalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

    // All withdrawals for a portfolio
    List<Withdrawal> findByPortfolioId(Long portfolioId);

    List<Withdrawal> findByPortfolio_Investor_Id(Long investorId);

}