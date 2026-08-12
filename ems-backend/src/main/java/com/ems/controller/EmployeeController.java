package com.ems.controller;

import com.ems.dto.EmployeeRequestDTO;
import com.ems.dto.EmployeeResponseDTO;
import com.ems.dto.EmployeeStatisticsDTO;
import com.ems.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST equivalent of the old console menu (options 1–17 in Main.java).
 * Each menu option there maps to one endpoint here.
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employees", description = "Add, update, delete, search, sort and filter employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Add a new employee")
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> addEmployee(@Valid @RequestBody EmployeeRequestDTO request) {
        EmployeeResponseDTO created = employeeService.addEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Get all employees, optionally sorted (sortBy=name|salary|joiningDate, order=asc|desc)")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order) {
        return ResponseEntity.ok(employeeService.getAllEmployees(sortBy, order));
    }

    @Operation(summary = "Get a single employee by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(summary = "Search employees by name (partial match)")
    @GetMapping("/search/name")
    public ResponseEntity<List<EmployeeResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.searchByName(name));
    }

    @Operation(summary = "Filter employees by department name")
    @GetMapping("/search/department")
    public ResponseEntity<List<EmployeeResponseDTO>> searchByDepartment(@RequestParam String departmentName) {
        return ResponseEntity.ok(employeeService.searchByDepartment(departmentName));
    }

    @Operation(summary = "Filter employees by salary range")
    @GetMapping("/search/salary")
    public ResponseEntity<List<EmployeeResponseDTO>> searchBySalaryRange(
            @RequestParam double minSalary,
            @RequestParam double maxSalary) {
        return ResponseEntity.ok(employeeService.searchBySalaryRange(minSalary, maxSalary));
    }

    @Operation(summary = "Update an existing employee")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO request) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    @Operation(summary = "Delete an employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get employee statistics (count, highest/lowest/average salary, department-wise count)")
    @GetMapping("/statistics")
    public ResponseEntity<EmployeeStatisticsDTO> getStatistics() {
        return ResponseEntity.ok(employeeService.getStatistics());
    }
}
