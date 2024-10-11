package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthyservicecategory;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateHealthServiceCategoryRequest {
    private String serviceName;
    private String serviceDescription;
    private Float price;
}
