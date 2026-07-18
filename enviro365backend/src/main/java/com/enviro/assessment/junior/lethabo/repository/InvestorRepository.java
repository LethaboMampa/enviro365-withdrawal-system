package com.enviro.assessment.junior.lethabo.repository;

import com.enviro.assessment.junior.lethabo.entity.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {

    Optional<Investor> findByEmail(String email);

    Optional<Investor> findByIdNumber(String idNumber);

    boolean existsByEmail(String email);

}