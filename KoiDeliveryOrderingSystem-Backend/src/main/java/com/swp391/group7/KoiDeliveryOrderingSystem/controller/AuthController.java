package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.AuthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterCustomerRequest registerCustomerRequest) throws MessagingException {
        authService.register(registerCustomerRequest);
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .code(200)
                .message("Register successful, Check your email to verify account")
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

    @GetMapping("send-verification-email")
    public ResponseEntity<ApiResponse<String>> sendVerificationEmail(@RequestParam("email") String email) throws MessagingException {
        authService.sendVerificationEmail(email);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("Email send successfully")
                .build());
    }

    @GetMapping("verify")
    public ModelAndView verify(@RequestParam("token") String token) {
        authService.verifyAccount(token);
        return new ModelAndView("verifySuccess");
    }

}
