package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.nimbusds.jose.util.health.HealthStatus;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.OrderDetail;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckingKoiHealthResponse {
    private Integer id;
    private Integer orderDetailId;
    private Integer packageId;
    private HealthStatus healthStatus;
    private String healthStatusDescription;
    private Float weight;
    private String color;
    private String type;
    private Integer age;
    private LocalDateTime createAt;
    private Integer createBy;
    private LocalDateTime updateAt;
    private Integer updateBy;
    private SystemStatusEnum status;
}
