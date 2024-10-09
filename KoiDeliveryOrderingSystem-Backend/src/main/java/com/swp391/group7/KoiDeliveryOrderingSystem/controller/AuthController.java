package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.AuthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.ChangePasswordRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.RegisterCustomerRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.AuthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("register")
    ApiResponse<AuthResponse> register(@RequestBody RegisterCustomerRequest registerCustomerRequest) {
        var result = authService.registerCustomer((registerCustomerRequest));
        return ApiResponse.<AuthResponse>builder()
                .code(200)
                .message("Register Successful")
                .result(result)
                .build();
    }
    @PostMapping("user-login")
    ApiResponse<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        var result = authService.authenticateUser(authRequest);
        return ApiResponse.<AuthResponse>builder()
                .code(200)
                .message("Login Successful")
                .result(result)
                .build();
    }

    @PostMapping("customer-login")
    ApiResponse<AuthResponse> authenticateCustomer(@RequestBody AuthRequest authRequest) {
        var result = authService.authenticateCustomer(authRequest);
        return ApiResponse.<AuthResponse>builder()
                .code(200)
                .message("Login Successful")
                .result(result)
                .build();
    }

    @PutMapping("change-password")
    ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        var result = authService.changePassword(changePasswordRequest);
        return ApiResponse.<String>builder()
                .code(200)
                .message(result)
                .build();
    }
}
