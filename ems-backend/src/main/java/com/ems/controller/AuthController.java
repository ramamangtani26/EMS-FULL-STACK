package com.ems.controller;

import com.ems.dto.LoginRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * NOTE: This is a deliberately lightweight, demo-level login for a
 * portfolio project — it is NOT production authentication. It checks a
 * single admin username/password from application.properties and returns
 * an opaque token the frontend stores and sends back, just to gate the
 * dashboard UI. For a production system this would be replaced with
 * Spring Security + JWT and a Users table with hashed passwords.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Demo login for the dashboard")
public class AuthController {

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Operation(summary = "Log in with the demo admin credentials")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        if (adminUsername.equals(request.getUsername()) && adminPassword.equals(request.getPassword())) {
            String token = UUID.randomUUID().toString();
            return ResponseEntity.ok(Map.of("token", token, "username", adminUsername));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid username or password"));
    }
}
