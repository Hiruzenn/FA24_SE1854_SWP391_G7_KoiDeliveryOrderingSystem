package com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.DeliveryStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHealthCareDeliveryHistoryRequest {
    private Integer orderId;
    private String route;
    private String healthDescription;
    private String eatingDescription;
}
