package com.ems.service.impl;

import com.ems.dto.EmployeeRequestDTO;
import com.ems.dto.EmployeeResponseDTO;
import com.ems.dto.EmployeeStatisticsDTO;
import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.exception.DepartmentNotFoundException;
import com.ems.exception.DuplicateEmployeeException;
import com.ems.exception.EmployeeNotFoundException;
import com.ems.mapper.EmployeeMapper;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import com.ems.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class carries forward the business rules from your original
 * service.EmployeeManager:
 *  - duplicate check before adding      (was: searchEmployeeIndex + DuplicateEmployeeException)
 *  - not-found check before update/delete (was: EmployeeNotFoundException)
 *  - statistics / department-wise count  (was: displayEmployeeStatistics / displayDepartmentWiseCount)
 *
 * The differences are just *where* the data lives and *who* is asking:
 *  - persistence is delegated to Spring Data JPA instead of DataStore<T> + a text file
 *  - the uniqueness key is email (employeeId is now DB-generated, so it can't collide)
 *  - results are returned as DTOs instead of printed to System.out
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper mapper;

    @Override
    @Transactional
    public EmployeeResponseDTO addEmployee(EmployeeRequestDTO request) {
        if (employeeRepository.existsByEmailIdIgnoreCase(request.getEmailId())) {
            throw new DuplicateEmployeeException(
                    "Employee with email " + request.getEmailId() + " already exists.");
        }

        Department department = getDepartmentOrThrow(request.getDepartmentId());

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmailId(request.getEmailId());
        employee.setContactNumber(request.getContactNumber());
        employee.setAddress(request.getAddress());
        employee.setDepartment(department);
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());

        Employee saved = employeeRepository.save(employee);
        log.info("Added new employee with id {}", saved.getEmployeeId());
        return mapper.toResponseDTO(saved);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees(String sortBy, String order) {
        Sort sort = buildSort(sortBy, order);
        return employeeRepository.findAll(sort).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long employeeId) {
        return mapper.toResponseDTO(getEmployeeOrThrow(employeeId));
    }

    @Override
    public List<EmployeeResponseDTO> searchByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDTO> searchByDepartment(String departmentName) {
        return employeeRepository.findByDepartment_DepartmentNameIgnoreCase(departmentName).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDTO> searchBySalaryRange(double minSalary, double maxSalary) {
        return employeeRepository.findBySalaryBetween(minSalary, maxSalary).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long employeeId, EmployeeRequestDTO request) {
        Employee employee = getEmployeeOrThrow(employeeId);

        // If the email changed, make sure it isn't already used by someone else
        if (!employee.getEmailId().equalsIgnoreCase(request.getEmailId())
                && employeeRepository.existsByEmailIdIgnoreCase(request.getEmailId())) {
            throw new DuplicateEmployeeException(
                    "Employee with email " + request.getEmailId() + " already exists.");
        }

        Department department = getDepartmentOrThrow(request.getDepartmentId());

        employee.setName(request.getName());
        employee.setEmailId(request.getEmailId());
        employee.setContactNumber(request.getContactNumber());
        employee.setAddress(request.getAddress());
        employee.setDepartment(department);
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());

        Employee updated = employeeRepository.save(employee);
        log.info("Updated employee with id {}", employeeId);
        return mapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long employeeId) {
        Employee employee = getEmployeeOrThrow(employeeId);
        employeeRepository.delete(employee);
        log.info("Deleted employee with id {}", employeeId);
    }

    @Override
    public EmployeeStatisticsDTO getStatistics() {
        List<Employee> all = employeeRepository.findAll();

        if (all.isEmpty()) {
            return new EmployeeStatisticsDTO(0, 0, 0, 0, Map.of());
        }

        double highest = all.stream().mapToDouble(Employee::getSalary).max().orElse(0);
        double lowest = all.stream().mapToDouble(Employee::getSalary).min().orElse(0);
        double average = all.stream().mapToDouble(Employee::getSalary).average().orElse(0);

        Map<String, Long> departmentWiseCount = all.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getDepartment().getDepartmentName(),
                        Collectors.counting()));

        return new EmployeeStatisticsDTO(all.size(), highest, lowest, average, departmentWiseCount);
    }

    // ---- helpers ----

    private Employee getEmployeeOrThrow(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID: " + employeeId + " not found"));
    }

    private Department getDepartmentOrThrow(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department with ID: " + departmentId + " not found"));
    }

    private Sort buildSort(String sortBy, String order) {
        String property = switch (sortBy == null ? "" : sortBy.toLowerCase()) {
            case "salary" -> "salary";
            case "joiningdate" -> "joiningDate";
            case "name" -> "name";
            default -> "employeeId";
        };
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(direction, property);
    }
}
