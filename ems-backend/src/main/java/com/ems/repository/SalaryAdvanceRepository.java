package com.ems.repository;

import com.ems.entity.SalaryAdvance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalaryAdvanceRepository extends JpaRepository<SalaryAdvance, Long> {
    List<SalaryAdvance> findByEmployee_EmployeeIdAndStatusNot(Long employeeId, SalaryAdvance.AdvanceStatus status);
    List<SalaryAdvance> findByStatusNot(SalaryAdvance.AdvanceStatus status);
}
