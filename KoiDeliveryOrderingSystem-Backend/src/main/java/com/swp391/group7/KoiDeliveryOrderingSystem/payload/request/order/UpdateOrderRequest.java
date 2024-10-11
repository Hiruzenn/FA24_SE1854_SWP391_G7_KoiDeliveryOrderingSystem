package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.order;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderRequest {
    private String deliveryMethod;
    private String destination;
    private String departure;
    private float distance;
    private String phone;
}
