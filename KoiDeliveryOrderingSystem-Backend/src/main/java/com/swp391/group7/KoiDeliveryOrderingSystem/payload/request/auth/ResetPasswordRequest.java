package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {
    @NotBlank(message = "New password is required")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "New password must be at least 6 characters long and contain at least one letter and one number"
    )
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "Confirm password must be at least 6 characters long and contain at least one letter and one number"
    )
    private String confirmPassword;

    @NotBlank(message = "Token is required")
    private String token;
}