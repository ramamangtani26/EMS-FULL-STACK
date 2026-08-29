package com.ems.controller;

import com.ems.dto.AdvanceRequestDTO;
import com.ems.dto.AdvanceResponseDTO;
import com.ems.service.SalaryAdvanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advances")
@RequiredArgsConstructor
@Tag(name = "Salary Advances", description = "Give advances and track month-end recovery")
public class SalaryAdvanceController {

    private final SalaryAdvanceService advanceService;

    @Operation(summary = "Give a new salary advance to an employee")
    @PostMapping
    public ResponseEntity<AdvanceResponseDTO> giveAdvance(@Valid @RequestBody AdvanceRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(advanceService.giveAdvance(request));
    }

    @Operation(summary = "Get all outstanding (not fully recovered) advances")
    @GetMapping
    public ResponseEntity<List<AdvanceResponseDTO>> getOutstanding() {
        return ResponseEntity.ok(advanceService.getOutstandingAdvances());
    }

    @Operation(summary = "Get outstanding advances for one employee")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AdvanceResponseDTO>> getForEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(advanceService.getOutstandingForEmployee(employeeId));
    }

    @Operation(summary = "Get total outstanding advance amount for one employee")
    @GetMapping("/employee/{employeeId}/total")
    public ResponseEntity<Double> getOutstandingTotal(@PathVariable Long employeeId) {
        return ResponseEntity.ok(advanceService.getOutstandingTotal(employeeId));
    }

    @Operation(summary = "Record a deduction against an advance (used during payroll run)")
    @PostMapping("/{advanceId}/deduct")
    public ResponseEntity<AdvanceResponseDTO> deduct(@PathVariable Long advanceId, @RequestParam double amount) {
        return ResponseEntity.ok(advanceService.recordDeduction(advanceId, amount));
    }
}
