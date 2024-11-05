package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.customer.UpdateProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.UserResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer") // Base URL for the certificate endpoints
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("get-profile")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile() {
        var result = userService.getCustomerProfile();
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Get Profile of Current User")
                .result(result)
                .build());
    }

    @PutMapping("update-profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@RequestBody UpdateProfileRequest request) {
        var result = userService.updateProfile(request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Update Profile Successfully")
                .result(result)
                .build());
    }
}
