package com.enviro.assessment.junior.lethabo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PortfolioResponseDTO {

    private Long id;
    private String portfolioName;
    private Double balance;
    private String portfolioType;
    private String investorName;

    private List<ProductResponseDTO> products;
}