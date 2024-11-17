package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HealthStatusEnum;
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
    @NotNull(message = "Health Status is required")
    private HealthStatusEnum healthStatus;

    @NotBlank(message = "Heal Status Description is required")
    private String healthStatusDescription;

    @NotNull(message = "Weight is required")
    private Float weight;

    @NotBlank(message = "Type is required")
    private String species;

    @NotBlank(message = "Color is required")
    private String color;

    @NotBlank(message = "Age is required")
    private String sex;
}
