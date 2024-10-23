package com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FishCategoryDTO {

    private Integer id;

    private String fish_category_name;

    private String fish_category_description;


}
