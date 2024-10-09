package com.swp391.group7.KoiDeliveryOrderingSystem.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
    private String deliveryMethod;
    private String destination;
    private String departure;
    private float distance;
    private String phone;
    private float amount;
}