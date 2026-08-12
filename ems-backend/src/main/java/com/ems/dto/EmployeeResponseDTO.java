package com.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/** What we send back to the client — includes the generated ID and full department info. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {

    private Long employeeId;
    private String name;
    private String emailId;
    private String contactNumber;
    private String address;
    private DepartmentDTO department;
    private double salary;
    private LocalDate joiningDate;
}
