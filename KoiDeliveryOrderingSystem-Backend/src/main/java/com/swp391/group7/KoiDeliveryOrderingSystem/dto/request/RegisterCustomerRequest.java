package com.swp391.group7.KoiDeliveryOrderingSystem.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterCustomerRequest {
    private String name;
    private String email;
    private String password;
}