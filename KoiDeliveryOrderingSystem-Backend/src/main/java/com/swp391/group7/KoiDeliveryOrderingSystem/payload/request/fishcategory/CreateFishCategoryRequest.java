package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory;


import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateFishCategoryRequest {


    private String fish_category_name;

    private String fish_category_description;

}
