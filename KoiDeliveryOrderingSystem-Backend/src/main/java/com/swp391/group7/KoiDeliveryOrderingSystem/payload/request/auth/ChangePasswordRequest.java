package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequest {
    @NotBlank(message = "Password is required")
    private String oldPassword;

    @NotBlank(message = "New Password is required")
    private String newPassword;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;
}
