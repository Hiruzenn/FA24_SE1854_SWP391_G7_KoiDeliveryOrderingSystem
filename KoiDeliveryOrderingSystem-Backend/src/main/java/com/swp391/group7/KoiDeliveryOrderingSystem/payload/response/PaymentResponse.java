package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.PaymentStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    private Integer id;
    private String code;
    private Integer customerId;
    private Integer orderId;
    private Float amount;
    private String method;
    private LocalDate paymentDate;
    private PaymentStatusEnum status;
}
