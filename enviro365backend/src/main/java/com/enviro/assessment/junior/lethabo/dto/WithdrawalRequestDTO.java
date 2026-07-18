package com.enviro.assessment.junior.lethabo.dto;

import lombok.Data;

@Data
public class WithdrawalRequestDTO {

    private Long portfolioId;
    private Double amount;
    private String reason;
}