package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String address;
    private String avatar;
}
