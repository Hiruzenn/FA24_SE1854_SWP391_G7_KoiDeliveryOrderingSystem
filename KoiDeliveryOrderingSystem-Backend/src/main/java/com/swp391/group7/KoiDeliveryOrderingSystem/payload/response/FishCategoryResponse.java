package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FishCategoryResponse {

    private int id;


    private String fishCategoryName;


    private String fishCategoryDescription;


    private int fishProfiles;
}
