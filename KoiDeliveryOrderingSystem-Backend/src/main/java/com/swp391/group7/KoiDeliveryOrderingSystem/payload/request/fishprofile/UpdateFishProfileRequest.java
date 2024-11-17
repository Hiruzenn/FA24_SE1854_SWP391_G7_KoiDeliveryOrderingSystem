package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishprofile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateFishProfileRequest {
    @NotBlank(message =  "Name is required")
    private String name;

    @NotBlank(message =  "Description is required")
    private String description;

    @NotBlank(message =  "Type is required")
    private String species;

    @NotBlank(message =  "Sex is required")
    private String sex;

    @NotBlank(message =  "Size is required")
    private String size;

    @NotNull(message = "Age is required")
    private Integer age;

    @NotBlank(message =  "Origin is required")
    private String origin;

    @NotNull(message = "Weight is required")
    private Float weight;

    @NotBlank(message =  "Color is required")
    private String color;

    @NotBlank(message =  "Image is required")
    private String image;
}
