package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse2 {
    private String code;
    private String message;
    private Object data;
}