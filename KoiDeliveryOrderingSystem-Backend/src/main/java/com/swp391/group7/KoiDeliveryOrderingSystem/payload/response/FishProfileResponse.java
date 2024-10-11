package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FishProfileResponse {
    private String name;
    private String description;
    private String type;
    private String size;
    private String origin;
    private String image;
    private LocalDateTime createAt;
    private  String createBy;
    private LocalDateTime updateAt;
    private  String updateBy;
    private SystemStatusEnum status;
}