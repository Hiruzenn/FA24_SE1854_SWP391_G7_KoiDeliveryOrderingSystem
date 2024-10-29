package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateFishCategoryRequest {
    @NotBlank(message = "Fish Category Name is required")
    private String fishCategoryName;

    @NotBlank(message = "Fish Category Description is required")
    private String fishCategoryDescription;
}
