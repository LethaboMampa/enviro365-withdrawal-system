package com.enviro.assessment.junior.lethabo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportFilterDTO {

    private LocalDate fromDate;
    private LocalDate toDate;

    private String status; // PENDING / APPROVED / REJECTED

    private Double amount;
    private String amountFilterType;
    // "GT" = greater than
    // "LT" = less than
    // "EQ" = equal
}