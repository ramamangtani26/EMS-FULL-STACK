package com.ems.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Used for both creating/updating a department and returning it to the client. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private Long departmentId;

    @NotBlank(message = "Department name cannot be empty.")
    private String departmentName;

    @NotBlank(message = "Location cannot be empty.")
    private String location;
}
