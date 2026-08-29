package com.ems.service;

import com.ems.dto.AdvanceRequestDTO;
import com.ems.dto.AdvanceResponseDTO;

import java.util.List;
void deleteAdvance(Long advanceId);

public interface SalaryAdvanceService {
    AdvanceResponseDTO giveAdvance(AdvanceRequestDTO request);
    List<AdvanceResponseDTO> getOutstandingAdvances();
    List<AdvanceResponseDTO> getOutstandingForEmployee(Long employeeId);
    double getOutstandingTotal(Long employeeId);
    AdvanceResponseDTO recordDeduction(Long advanceId, double amount);
}
