package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.orderdetail;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderDetailRequest {
    @Positive(message = "quantity must be positive")
    private Integer quantity;
}
