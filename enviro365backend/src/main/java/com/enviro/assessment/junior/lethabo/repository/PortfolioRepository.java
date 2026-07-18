package com.enviro.assessment.junior.lethabo.repository;

import com.enviro.assessment.junior.lethabo.entity.Portfolio;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    // Get all portfolios for an investor
    List<Portfolio> findByInvestorId(Long investorId);

    // Prevent LazyLoading issues + fetch products & withdrawals
    @EntityGraph(attributePaths = {"products", "withdrawals"})
    Optional<Portfolio> findById(Long id);
}