package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDeliveryMethodRequest {
    @NotBlank(message = "Delivery Method Name is required")
    private String deliveryMethodName;
}
