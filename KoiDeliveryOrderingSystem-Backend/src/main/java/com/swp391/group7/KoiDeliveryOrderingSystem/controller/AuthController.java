package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.dto.request.AuthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.response.AuthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.AuthService;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("user-login")
    ApiResponse<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        var  result = authService.authenticateUser(authRequest);
        return ApiResponse.<AuthResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("customer-login")
    ApiResponse<AuthResponse> authenticateCustomer(@RequestBody AuthRequest authRequest) {
        var  result = authService.authenticateCustomer(authRequest);
        return ApiResponse.<AuthResponse>builder()
                .result(result)
                .build();
    }

}
