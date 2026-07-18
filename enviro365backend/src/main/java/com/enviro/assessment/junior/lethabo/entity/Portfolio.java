package com.enviro.assessment.junior.lethabo.entity;

//imports
import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

import lombok.*;

import java.time.LocalDateTime;

import java.util.*;


@Builder
@Entity
@Table(name = "portfolios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

    //Auto-generated id for portfolio
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Portfolio name to give portfolio meaning
    @NotBlank(message = "Portfolio name is required")
    @Column(nullable = false, length = 255)
    private String portfolioName;

    //Balance for portfolio
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    @Column(nullable = false)
    private Double balance;

    //Portfolio Type to give them differences
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PortfolioType portfolioType;

    //Creation date for specidic portfolio
    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Many portfolios belong to one investor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investor_id", nullable = false)
    @JsonBackReference
    private Investor investor;

    // One portfolio has many products
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

    // One portfolio has many withdrawals
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Withdrawal> withdrawals = new ArrayList<>();

    public enum PortfolioType {
        RETIREMENT,
        SAVINGS,
        INVESTMENT
    }
}