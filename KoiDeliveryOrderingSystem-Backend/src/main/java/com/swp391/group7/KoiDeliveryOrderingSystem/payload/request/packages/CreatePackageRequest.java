package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.packages;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.PackageStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePackageRequest {
    private String description;
    private String image;
}
