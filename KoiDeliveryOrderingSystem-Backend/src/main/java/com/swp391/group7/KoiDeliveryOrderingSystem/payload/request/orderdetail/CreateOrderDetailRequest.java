package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.orderdetail;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderDetailRequest {
    @NotNull(message = "Order Id is required")
    private Integer orderId;

    @NotNull(message = "Fish Profile Id is required")
    private Integer fishProfileId;

    @Positive(message = "quantity must be positive")
    private Integer quantity;
}
