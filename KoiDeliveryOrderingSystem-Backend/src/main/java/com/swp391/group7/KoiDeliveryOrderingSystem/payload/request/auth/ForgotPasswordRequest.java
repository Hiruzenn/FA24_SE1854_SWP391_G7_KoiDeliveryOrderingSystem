package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth;

import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgotPasswordRequest {
    @Email(message = "Email invalid")
    private String email;
}
