package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    private FishProfile fishProfile;
    private Integer quantity;
    private Float unitPrice;
    private Float amount;
    private LocalDateTime receivingDate;
    private LocalDateTime createAt;
    private String createBy;
    private LocalDateTime updateAt;
    private String updateBy;
    private SystemStatusEnum status;
}