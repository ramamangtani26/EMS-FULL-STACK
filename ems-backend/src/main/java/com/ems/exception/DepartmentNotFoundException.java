package com.ems.exception;

/** New — same pattern as EmployeeNotFoundException, for the Department module. */
public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(String msg) {
        super(msg);
    }
}
