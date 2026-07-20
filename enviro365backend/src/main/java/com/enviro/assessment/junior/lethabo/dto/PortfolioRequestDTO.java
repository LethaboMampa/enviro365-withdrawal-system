package com.enviro.assessment.junior.lethabo.dto;

import com.enviro.assessment.junior.lethabo.entity.Portfolio.PortfolioType;
import lombok.Data;

@Data
public class PortfolioRequestDTO {

    private Long investorId;
    private String portfolioName;
    private Double balance;
    private PortfolioType portfolioType;
}