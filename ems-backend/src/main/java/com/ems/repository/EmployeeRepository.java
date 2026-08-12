package com.ems.repository;

import com.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Replaces your old EmployeeRepository interface + EmployeeFileRepository
 * (employees.txt) implementation. Spring Data JPA generates the
 * implementation at runtime from these method names — no more manual
 * BufferedReader/BufferedWriter parsing.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmailIdIgnoreCase(String emailId);

    Optional<Employee> findByEmailIdIgnoreCase(String emailId);

    List<Employee> findByNameContainingIgnoreCase(String name);

    List<Employee> findByDepartment_DepartmentNameIgnoreCase(String departmentName);

    List<Employee> findBySalaryBetween(double minSalary, double maxSalary);
}
