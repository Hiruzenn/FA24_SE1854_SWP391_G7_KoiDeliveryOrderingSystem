package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HealthStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.OrderDetail;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Order Detail Id is required")
    private Integer orderDetailId;

    @NotNull(message = "Package Id is required")
    private Integer packageId;
    @NotNull(message = "Health Status is required")
    private HealthStatusEnum healthStatus;

    @NotBlank(message = "Heal Status Description is required")
    private String healthStatusDescription;

    @NotNull(message = "Weight is required")
    private Float weight;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Color is required")
    private String color;

    @NotNull(message = "Age is required")
    private Integer age;
}
