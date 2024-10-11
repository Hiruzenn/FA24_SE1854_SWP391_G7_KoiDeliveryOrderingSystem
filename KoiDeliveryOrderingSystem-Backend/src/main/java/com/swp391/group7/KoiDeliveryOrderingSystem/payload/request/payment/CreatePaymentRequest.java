package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.payment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePaymentRequest {
    private String paymentMethod;
    private float amount;
}
