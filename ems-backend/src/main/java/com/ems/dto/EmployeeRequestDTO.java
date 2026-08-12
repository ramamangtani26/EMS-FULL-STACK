package com.ems.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * What the client sends us on create/update. Kept separate from the
 * response DTO (and from the entity) so we never expose or accept more
 * than the API contract needs — this is the DTO pattern requested.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {

    @NotBlank(message = "Name cannot be empty.")
    private String name;

    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Email must be a valid email address.")
    private String emailId;

    @NotBlank(message = "Contact Number cannot be empty.")
    @Pattern(regexp = "\\d{10}", message = "Contact number must be exactly 10 digits.")
    private String contactNumber;

    @NotBlank(message = "Address cannot be empty.")
    private String address;

    @NotNull(message = "Department is required.")
    private Long departmentId;

    @PositiveOrZero(message = "Salary cannot be negative.")
    private double salary;

    @NotNull(message = "Joining Date cannot be null.")
    private LocalDate joiningDate;
}
