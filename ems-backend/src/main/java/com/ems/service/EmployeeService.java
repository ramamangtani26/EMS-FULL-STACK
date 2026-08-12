package com.ems.service;

import com.ems.dto.EmployeeRequestDTO;
import com.ems.dto.EmployeeResponseDTO;
import com.ems.dto.EmployeeStatisticsDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO addEmployee(EmployeeRequestDTO request);

    List<EmployeeResponseDTO> getAllEmployees(String sortBy, String order);

    EmployeeResponseDTO getEmployeeById(Long employeeId);

    List<EmployeeResponseDTO> searchByName(String name);

    List<EmployeeResponseDTO> searchByDepartment(String departmentName);

    List<EmployeeResponseDTO> searchBySalaryRange(double minSalary, double maxSalary);

    EmployeeResponseDTO updateEmployee(Long employeeId, EmployeeRequestDTO request);

    void deleteEmployee(Long employeeId);

    EmployeeStatisticsDTO getStatistics();
}
