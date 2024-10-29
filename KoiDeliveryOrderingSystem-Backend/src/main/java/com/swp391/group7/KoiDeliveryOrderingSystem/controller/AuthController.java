package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.AuthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.ChangePasswordRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.RegisterCustomerRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.AuthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterCustomerRequest registerCustomerRequest) {
        AuthResponse result = authService.register(registerCustomerRequest);
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .code(200)
                .message("Register successful")
                .result(result)
                .build());
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse result = authService.login(authRequest);
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .code(200)
                .message("Login successful")
                .result(result)
                .build());
    }

    @PutMapping("change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        String result = authService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("Password changed successfully")
                .result(result)
                .build());
    }
}
