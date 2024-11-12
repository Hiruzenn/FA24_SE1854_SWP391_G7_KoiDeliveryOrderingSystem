package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
    @NotBlank(message = "Delivery Method is required")
    private String deliveryMethod;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotBlank(message = "Departure is required")
    private String departure;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @NotBlank(message = "Phone is required")
    private String phone;
}
