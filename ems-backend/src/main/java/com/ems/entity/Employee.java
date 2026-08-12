package com.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * JPA version of the original model.Employee class.
 * The field-level validation you wrote in the setters (non-null checks,
 * "cannot be empty" messages) is preserved here as Bean Validation
 * annotations instead — Spring validates these automatically on every
 * incoming request before the controller method even runs.
 */
@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotBlank(message = "Name cannot be empty.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Email must be a valid email address.")
    @Column(nullable = false, unique = true)
    private String emailId;

    @NotBlank(message = "Contact Number cannot be empty.")
    @Pattern(regexp = "\\d{10}", message = "Contact number must be exactly 10 digits.")
    @Column(nullable = false)
    private String contactNumber;

    @NotBlank(message = "Address cannot be empty.")
    @Column(nullable = false)
    private String address;

    @NotNull(message = "Department cannot be null.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @PositiveOrZero(message = "Salary cannot be negative.")
    @Column(nullable = false)
    private double salary;

    @NotNull(message = "Joining Date cannot be null.")
    @Column(nullable = false)
    private LocalDate joiningDate;
}
