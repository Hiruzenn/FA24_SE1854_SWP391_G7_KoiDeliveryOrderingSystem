package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.user;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.CustomerStatusEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    @NotBlank(message = "Name is required")
    String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Password is required")
    String password;

    @NotBlank(message = "address is required")
    String address;

    @NotBlank(message = "avatar is required")
    String avatar;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    String phone;

    @NotBlank(message = "Role is required")
    String role;
}
