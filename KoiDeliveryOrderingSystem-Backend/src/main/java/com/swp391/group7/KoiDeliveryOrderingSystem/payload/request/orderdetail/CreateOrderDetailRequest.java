package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.orderdetail;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderDetailRequest {
    private Integer quantity;
    private Float unitPrice;
}
