package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.AuthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.ChangePasswordRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.RegisterCustomerRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.AuthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("register")
    ApiResponse<AuthResponse> register(@RequestBody RegisterCustomerRequest registerCustomerRequest) {
        var result = authService.register((registerCustomerRequest));
        return ApiResponse.<AuthResponse>builder()
                .code(200)
                .message("Register Successful")
                .result(result)
                .build();
    }
    @PostMapping("login")
    ApiResponse<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        var result = authService.login(authRequest);
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
