package com.ems.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AdvanceRequestDTO {
    @NotNull private Long employeeId;
    @Positive private double amount;
    @NotNull private LocalDate dateGiven;
    private String reason;
}
