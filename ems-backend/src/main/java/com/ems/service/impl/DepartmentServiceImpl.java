package com.ems.service.impl;

import com.ems.dto.DepartmentDTO;
import com.ems.entity.Department;
import com.ems.exception.DepartmentNotFoundException;
import com.ems.exception.DuplicateEmployeeException;
import com.ems.mapper.EmployeeMapper;
import com.ems.repository.DepartmentRepository;
import com.ems.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper mapper;

    @Override
    @Transactional
    public DepartmentDTO addDepartment(DepartmentDTO request) {
        if (departmentRepository.existsByDepartmentNameIgnoreCase(request.getDepartmentName())) {
            throw new DuplicateEmployeeException(
                    "Department '" + request.getDepartmentName() + "' already exists.");
        }
        Department department = new Department(null, request.getDepartmentName(), request.getLocation());
        Department saved = departmentRepository.save(department);
        log.info("Added new department with id {}", saved.getDepartmentId());
        return mapper.toDepartmentDTO(saved);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(mapper::toDepartmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        return mapper.toDepartmentDTO(getDepartmentOrThrow(id));
    }

    @Override
    @Transactional
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO request) {
        Department department = getDepartmentOrThrow(id);
        department.setDepartmentName(request.getDepartmentName());
        department.setLocation(request.getLocation());
        Department updated = departmentRepository.save(department);
        log.info("Updated department with id {}", id);
        return mapper.toDepartmentDTO(updated);
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        Department department = getDepartmentOrThrow(id);
        departmentRepository.delete(department);
        log.info("Deleted department with id {}", id);
    }

    private Department getDepartmentOrThrow(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department with ID: " + id + " not found"));
    }
}
