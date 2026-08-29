package com.ems.service.impl;

import com.ems.dto.AdvanceRequestDTO;
import com.ems.dto.AdvanceResponseDTO;
import com.ems.entity.Employee;
import com.ems.entity.SalaryAdvance;
import com.ems.exception.AdvanceNotFoundException;
import com.ems.exception.EmployeeNotFoundException;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.SalaryAdvanceRepository;
import com.ems.service.SalaryAdvanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaryAdvanceServiceImpl implements SalaryAdvanceService {

    private final SalaryAdvanceRepository advanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public AdvanceResponseDTO giveAdvance(AdvanceRequestDTO request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee with ID: " + request.getEmployeeId() + " not found"));

        SalaryAdvance advance = new SalaryAdvance();
        advance.setEmployee(employee);
        advance.setAmount(request.getAmount());
        advance.setDateGiven(request.getDateGiven());
        advance.setReason(request.getReason());

        return toDTO(advanceRepository.save(advance));
    }

    @Override
    public List<AdvanceResponseDTO> getOutstandingAdvances() {
        return advanceRepository.findByStatusNot(SalaryAdvance.AdvanceStatus.RECOVERED).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvanceResponseDTO> getOutstandingForEmployee(Long employeeId) {
        return advanceRepository
                .findByEmployee_EmployeeIdAndStatusNot(employeeId, SalaryAdvance.AdvanceStatus.RECOVERED).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public double getOutstandingTotal(Long employeeId) {
        return advanceRepository
                .findByEmployee_EmployeeIdAndStatusNot(employeeId, SalaryAdvance.AdvanceStatus.RECOVERED)
                .stream()
                .mapToDouble(a -> a.getAmount() - a.getAmountRecovered())
                .sum();
    }

    @Override
    @Transactional
    public AdvanceResponseDTO recordDeduction(Long advanceId, double amount) {
        SalaryAdvance advance = advanceRepository.findById(advanceId)
                .orElseThrow(() -> new AdvanceNotFoundException("Advance with ID: " + advanceId + " not found"));

        double newRecovered = advance.getAmountRecovered() + amount;
        advance.setAmountRecovered(Math.min(newRecovered, advance.getAmount()));
        advance.setStatus(advance.getAmountRecovered() >= advance.getAmount()
                ? SalaryAdvance.AdvanceStatus.RECOVERED
                : SalaryAdvance.AdvanceStatus.PARTIALLY_RECOVERED);

        return toDTO(advanceRepository.save(advance));
    }

    private AdvanceResponseDTO toDTO(SalaryAdvance a) {
        return new AdvanceResponseDTO(
                a.getAdvanceId(), a.getEmployee().getEmployeeId(), a.getEmployee().getName(),
                a.getAmount(), a.getAmountRecovered(), a.getAmount() - a.getAmountRecovered(),
                a.getDateGiven(), a.getReason(), a.getStatus().name());
    }
}
