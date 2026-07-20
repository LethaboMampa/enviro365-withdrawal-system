package com.enviro.assessment.junior.lethabo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WithdrawalReportDTO {

    // Investor
    private Long investorId;
    private String investorName;
    private String investorEmail;

    // Portfolio
    private Long portfolioId;
    private String portfolioName;
    private String portfolioType;
    private Double portfolioBalance;

    // Withdrawal
    private Long withdrawalId;
    private Double amount;
    private String reason;
    private String status;
    private LocalDateTime withdrawalDate;

    // Product summary (simple text for CSV)
    private String products;
}