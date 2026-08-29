package com.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
public class AdvanceResponseDTO {
    private Long advanceId;
    private Long employeeId;
    private String employeeName;
    private double amount;
    private double amountRecovered;
    private double outstanding;
    private LocalDate dateGiven;
    private String reason;
    private String status;
}
