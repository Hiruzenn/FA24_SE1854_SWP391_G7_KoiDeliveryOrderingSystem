package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthyservicecategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateHealthServiceCategoryRequest {
    @NotBlank(message = "Service Name is required")
    private String serviceName;

    @NotBlank(message = "Service Description is required")
    private String serviceDescription;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Float price;
}
