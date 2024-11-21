package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.AuthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
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

    @PostMapping("send-verification-email")
    public ResponseEntity<ApiResponse<String>> sendVerificationEmail(@RequestParam("email") String email) throws MessagingException {
        authService.sendVerificationEmail(email);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("Email send successfully")
                .build());
    }

    @PostMapping("forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) throws MessagingException {
        authService.sendResetPasswordEmail(request.getEmail());
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("Reset password email sent")
                .build());
    }

    @PostMapping("reset-password")
    public ModelAndView resetPassword(@RequestParam("token") String token,
                                      @RequestParam("newPassword") String newPassword,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      RedirectAttributes redirectAttributes) throws MessagingException {
        ModelAndView modelAndView = new ModelAndView();
        try {
            authService.resetPassword(token, newPassword, confirmPassword);
            modelAndView.setViewName("ResetPasswordSuccess");

        } catch (AppException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            modelAndView.setViewName("redirect:/auth/reset-password?token=" + token);
        }
        return modelAndView;
    }

    @GetMapping("reset-password")
    public ModelAndView showResetPasswordPage(@RequestParam("token") String token) {
        return new ModelAndView("ResetPassword");
    }

    @GetMapping("verify")
    public ModelAndView verify(@RequestParam("token") String token) {
        authService.verifyAccount(token);
        return new ModelAndView("VerifySuccess");
    }

}
