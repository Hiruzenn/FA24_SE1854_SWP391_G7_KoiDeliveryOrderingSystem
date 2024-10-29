package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthserviceorder;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateHealthServiceOrderRequest {
    @NotNull(message = "Order Id is required")
    private Integer orderId;

    @NotNull(message = "Health Service Category Id is required")
    private Integer HealthServiceCategoryId;
}
