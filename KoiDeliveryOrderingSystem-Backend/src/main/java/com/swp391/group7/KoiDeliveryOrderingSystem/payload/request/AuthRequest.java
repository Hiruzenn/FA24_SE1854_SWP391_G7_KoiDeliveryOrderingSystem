package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthRequest {
    private String email;
    private String password;
}
