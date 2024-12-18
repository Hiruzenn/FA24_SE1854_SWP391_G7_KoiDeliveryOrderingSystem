package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.user.CreateUserRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.user.UpdateProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.UserResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@Tag(name = "Customer")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("view-profile")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile() {
        var result = userService.getCustomerProfile();
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Get Profile of Current User")
                .result(result)
                .build());
    }

    @GetMapping("view-by-id/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Integer userId) {
        var result = userService.viewById(userId);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Get Profile By Id")
                .result(result)
                .build());
    }

    @PutMapping("update-profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        var result = userService.updateProfile(request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Update Profile Successfully")
                .result(result)
                .build());
    }

    @PutMapping("edit-user/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> editUser(@PathVariable("userId") Integer userId, @RequestBody UpdateProfileRequest request) {
        var result = userService.editProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Edit Profile Successfully")
                .result(result)
                .build());
    }

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<UserResponse>>> viewAll() {
        var result = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .message("View All Users")
                .result(result)
                .build());
    }

    @GetMapping("view-delivery-staff")
    public ResponseEntity<ApiResponse<List<UserResponse>>> viewDeliveryStaff() {
        var result = userService.viewDeliveryStaff();
        return ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .message("View All Delivery Staff")
                .result(result)
                .build());
    }

    @PostMapping("create-user")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        var result = userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Create User Successfully")
                .result(result)
                .build());
    }

    @PutMapping("block-unblock/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> blockUnblock(@PathVariable("userId") Integer userId) {
        var result = userService.blockUnblockUser(userId);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Block Unblock Successfully")
                .result(result)
                .build());
    }
}
