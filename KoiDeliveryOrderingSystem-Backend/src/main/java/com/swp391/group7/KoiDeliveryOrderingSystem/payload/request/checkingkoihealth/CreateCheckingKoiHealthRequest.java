package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HealthStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.OrderDetail;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCheckingKoiHealthRequest {
    private HealthStatusEnum healthStatus;
    private String healthStatusDescription;
    private Float weight;
    private String type;
    private String color;
    private Integer age;
    private Float price;
}
