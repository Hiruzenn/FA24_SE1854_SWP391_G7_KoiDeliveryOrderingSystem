package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishprofile;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateFishProfileRequest {
    @NotBlank(message =  "Name is required")
    private String name;

    @NotBlank(message =  "Description is required")
    private String description;

    @NotBlank(message =  "Type is required")
    private String type;

    @NotBlank(message =  "Size is required")
    private String size;

    @NotBlank(message =  "Origin is required")
    private String origin;

    @NotBlank(message =  "Image is required")
    private String image;
}
