package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class FishCategoryResponse {
    private Integer id;
    private String fish_category_name;
    private String fish_category_description;

}