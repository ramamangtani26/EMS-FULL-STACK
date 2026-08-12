package com.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Mirrors the numbers your old displayEmployeeStatistics() / displayDepartmentWiseCount()
 * printed to the console — now returned as JSON for the dashboard to render.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeStatisticsDTO {
    private long totalEmployees;
    private double highestSalary;
    private double lowestSalary;
    private double averageSalary;
    private Map<String, Long> departmentWiseCount;
}
