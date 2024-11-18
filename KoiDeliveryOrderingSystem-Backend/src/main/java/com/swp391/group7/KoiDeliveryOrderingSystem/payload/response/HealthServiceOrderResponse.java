package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthServiceOrderResponse {
    private Integer id;
    private HealthServiceCategory healthServiceCategory;
    private Integer orderId;
    private LocalDateTime createAt;
    private Integer createBy;
}
