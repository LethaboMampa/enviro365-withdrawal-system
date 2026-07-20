package com.enviro.assessment.junior.lethabo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InvestorProfileDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String idNumber;
    private LocalDate dateOfBirth;
}