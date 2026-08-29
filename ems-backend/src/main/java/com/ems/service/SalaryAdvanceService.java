public interface SalaryAdvanceService {
    AdvanceResponseDTO giveAdvance(AdvanceRequestDTO request);
    List<AdvanceResponseDTO> getOutstandingAdvances();               // all pending, any employee
    List<AdvanceResponseDTO> getOutstandingForEmployee(Long employeeId);
    double getOutstandingTotal(Long employeeId);                     // used in payroll calc
    AdvanceResponseDTO recordDeduction(Long advanceId, double amount); // called at month-end payroll run
}
