package com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.DeliveryStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHealthCareDeliveryHistoryRequest {
    @NotBlank(message = "Route is required")
    private String route;

    @NotBlank(message = "Health Description is required")
    private String healthDescription;

    @NotBlank(message = "Eating Description is required")
    private String eatingDescription;
}
