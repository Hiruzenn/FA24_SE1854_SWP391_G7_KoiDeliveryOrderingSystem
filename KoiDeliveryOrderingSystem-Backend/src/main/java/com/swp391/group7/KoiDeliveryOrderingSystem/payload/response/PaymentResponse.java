package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Customers;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    private String paymentCode;
    private Customers customers;
    private Orders orders;
    private Float amount;
    private String paymentMethod;
    private LocalDateTime createAt;
    private String createBy;
    private LocalDateTime updateAt;
    private String updateBy;
    private SystemStatusEnum statusEnum;
}
