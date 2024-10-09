package com.swp391.group7.KoiDeliveryOrderingSystem.dto.request;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateFishProfileRequest {
    private String name;
    private String description;
    private String type;
    private String size;
    private String origin;
    private String image;
}
