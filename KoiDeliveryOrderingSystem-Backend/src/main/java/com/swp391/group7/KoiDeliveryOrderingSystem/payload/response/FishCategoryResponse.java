package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FishCategoryResponse {

    private int id;
    private String fish_category_name;
    private String fish_category_description;
}
