package com.enviro.assessment.junior.lethabo.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private Long id;
    private String productName;
    private String productType;
    private Double units;
    private Double unitPrice;
    private Double value;
}