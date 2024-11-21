package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FishCategoryResponse {
    private int id;
    private String fishCategoryName;
    private String fishCategoryDescription;
    private Float price;
    private LocalDateTime createAt;
    private Integer createBy;
    private LocalDateTime updateAt;
    private Integer updateBy;
}
