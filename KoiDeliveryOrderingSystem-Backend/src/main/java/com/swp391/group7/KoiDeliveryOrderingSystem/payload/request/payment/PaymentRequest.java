package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.payment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    String code;
    String message;
    String paymentUrl;
}
