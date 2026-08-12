package com.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA version of the original model.Department class.
 * Same three fields you had (name, location) — now DB-backed instead of
 * living only in memory.
 */
@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @NotBlank(message = "Department name cannot be empty.")
    @Column(nullable = false, unique = true)
    private String departmentName;

    @NotBlank(message = "Location cannot be empty.")
    @Column(nullable = false)
    private String location;
}
