package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderRequest {
    @NotBlank(message = "Delivery Method is required")
    private String deliveryMethod;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotBlank(message = "Departure is required")
    private String departure;

    @Positive(message = "Distance must be positive")
    @NotNull(message = "Distance is required")
    private float distance;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @NotBlank(message = "Phone is required")
    private String phone;

    @Positive(message = "Amount is must be positive")
    @NotNull(message = "Amount is required")
    private float amount;

    @Positive(message = "VAT is must be positive")
    @NotNull(message = "VAT is required")
    private float vat;

    @Positive(message = "VAT amount is must be positive")
    @NotNull(message = "VAT Amount is required")
    private float vatAmount;

    @Positive(message = "Total Amount is must be positive")
    @NotNull(message = "Total Amount is required")
    private float totalAmount;

    private LocalDateTime estimateDeliveryDate;

    private LocalDateTime receivingDate;
}
