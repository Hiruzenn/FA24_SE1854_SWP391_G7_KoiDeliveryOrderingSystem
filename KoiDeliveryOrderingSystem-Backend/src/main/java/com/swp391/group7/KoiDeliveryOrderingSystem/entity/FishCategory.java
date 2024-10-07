package com.swp391.group7.KoiDeliveryOrderingSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class FishCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fish_category_name")
    private String fishCategoryName;

    @Column(name = "fish_category_description")
    private String fishCategoryDescription;

    @OneToMany(mappedBy = "type")
    private List<FishProfile> fishProfiles;

}
