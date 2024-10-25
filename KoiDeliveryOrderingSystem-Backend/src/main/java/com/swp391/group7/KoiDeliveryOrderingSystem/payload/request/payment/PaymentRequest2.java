package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest2 {
    private String bankCode;
    private int orderId;
    private float totalAmount;
    private String code;
    private String message;
    private String paymentUrl;
}
