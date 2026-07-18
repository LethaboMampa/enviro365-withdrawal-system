package com.enviro.assessment.junior.lethabo.entity;

//imports
import jakarta.persistence.*;

import jakarta.validation.constraints.*;

import lombok.*;

import java.time.*;

import java.util.*;

/**
 * Represents an investor in the system.
 * An investor owns a portfolio and can make withdrawal requests.
 */
@Builder
@Entity
@Table(name = "investors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investor {

    //Auto-generated id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Full name of the investor
    @NotBlank(message = "Full name is required")
    @Column(nullable = false,length = 100)
    private String fullName;

    //Email of the investor
    @Email(message = "Email must be valid")
    @Column(nullable = true,unique = true)
    private String email;

    //Phone number of the investor
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;

    //Unique id number of investor
    @NotBlank(message = "IdNumber is required")
    @Column(nullable = false,unique = true)
    private String idNumber;

    //address of the investor
    private String address;

    // DOB is important for retirement withdrawal rule (>65)
    @NotNull(message = "Date of birth is required")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    //Password for now will be stored as entered can be modified later on production
    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    //Creation date for specific investor
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    // One investor has one portfolio
    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL)
    private List<Portfolio> portfolios = new ArrayList<>();

}