package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceCategory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
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
    private Integer healthServiceCategoryId;
    private Integer orderId;
    private LocalDateTime createAt;
    private Integer createBy;
    private LocalDateTime updateAt;
    private Integer updateBy;
    private SystemStatusEnum status;
}
