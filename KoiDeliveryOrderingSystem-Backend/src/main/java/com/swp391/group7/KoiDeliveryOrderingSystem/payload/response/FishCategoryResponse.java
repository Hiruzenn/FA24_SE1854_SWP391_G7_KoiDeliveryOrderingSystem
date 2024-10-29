package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FishCategoryResponse {
    private int id;
    private String fishCategoryName;
    private String fishCategoryDescription;
    private LocalDateTime createAt;
    private Integer createBy;
    private LocalDateTime updateAt;
    private Integer updateBy;
    private SystemStatusEnum status;
}
