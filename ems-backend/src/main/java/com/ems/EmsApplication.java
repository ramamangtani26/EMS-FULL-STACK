package com.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Employee Management System backend.
 * This replaces the old console Main.java — Spring Boot now owns the
 * application lifecycle instead of a Scanner-driven while(true) loop.
 */
@SpringBootApplication
public class EmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmsApplication.class, args);
    }
}
