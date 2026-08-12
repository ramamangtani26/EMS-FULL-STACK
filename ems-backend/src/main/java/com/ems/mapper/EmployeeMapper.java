package com.ems.mapper;

import com.ems.dto.DepartmentDTO;
import com.ems.dto.EmployeeResponseDTO;
import com.ems.entity.Department;
import com.ems.entity.Employee;
import org.springframework.stereotype.Component;

/**
 * Manual, explicit mapping between entities and DTOs. Kept simple and
 * dependency-free (no MapStruct) so it's easy to read for a fresher
 * project — this is a small enough object graph that a mapper library
 * would be overkill.
 */
@Component
public class EmployeeMapper {

    public EmployeeResponseDTO toResponseDTO(Employee employee) {
        DepartmentDTO departmentDTO = toDepartmentDTO(employee.getDepartment());
        return new EmployeeResponseDTO(
                employee.getEmployeeId(),
                employee.getName(),
                employee.getEmailId(),
                employee.getContactNumber(),
                employee.getAddress(),
                departmentDTO,
                employee.getSalary(),
                employee.getJoiningDate()
        );
    }

    public DepartmentDTO toDepartmentDTO(Department department) {
        if (department == null) {
            return null;
        }
        return new DepartmentDTO(
                department.getDepartmentId(),
                department.getDepartmentName(),
                department.getLocation()
        );
    }
}
