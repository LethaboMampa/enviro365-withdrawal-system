package com.enviro.assessment.junior.lethabo.entity;

//imports
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "withdrawals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Withdrawal {

    //Auto-generated id for withdrawal
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Amount of the withdrawal
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Withdrawal amount must be greater than 0")
    @Column(nullable = false)
    private Double amount;

    //Reason to why withdrawal being made
    private String reason;

    //Creation date of the withdrawal
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime withdrawalDate = LocalDateTime.now();

    //Withdrawal status
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WithdrawalStatus status = WithdrawalStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    @JsonBackReference
    private Portfolio portfolio;

    public enum WithdrawalStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}