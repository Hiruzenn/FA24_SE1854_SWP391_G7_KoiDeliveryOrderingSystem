package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDeliveryMethodRequest {
    @NotBlank(message = "Delivery Method Name is required")
    private String name;

    @NotBlank(message = "Delivery Method Name is required")
    private String description;

    @NotNull(message = "Price is required")
    private Float price;
}
