package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDeliveryMethodRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Name is required")
    private String description;

    @NotNull(message = "Price is required")
    private Float price;
}
