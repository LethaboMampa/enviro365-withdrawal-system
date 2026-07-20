package com.enviro.assessment.junior.lethabo.entity;

//imports
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    //Auto-generated if for product
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Product name to give product meaning
    @NotBlank(message = "Product name is required")
    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    //Product type to give difference
    @NotNull(message = "Product type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    //Units to check available units per product
    @NotNull(message = "Units are required")
    @DecimalMin(value = "0.0", message = "Units cannot be negative")
    @Column(nullable = false)
    private Double units;

    //Unit price for calculation of product price
    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", message = "Unit price cannot be negative")
    @Column(nullable = false, name = "unit_price")
    private Double unitPrice;

    //Creation date for product
    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Many products belong to one portfolio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    @JsonBackReference
    private Portfolio portfolio;

    // Calculated field (not stored in DB)
    @Transient
    public Double getValue() {
        return units * unitPrice;
    }

    public enum ProductType {
        RETIREMENT,
        EQUITY,
        BOND,
        CASH
    }
}