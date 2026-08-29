package com.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "salary_advances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryAdvance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advanceId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Positive(message = "Advance amount must be greater than zero.")
    @Column(nullable = false)
    private double amount;

    // how much of this advance has already been recovered from salary
    @PositiveOrZero
    @Column(nullable = false)
    private double amountRecovered = 0;

    @NotNull
    @Column(nullable = false)
    private LocalDate dateGiven;

    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdvanceStatus status = AdvanceStatus.PENDING;

    public enum AdvanceStatus { PENDING, PARTIALLY_RECOVERED, RECOVERED }
}
