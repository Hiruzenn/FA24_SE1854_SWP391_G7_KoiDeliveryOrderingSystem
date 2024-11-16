package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.PackageStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageResponse {
    private Integer id;
    private String packageNo;
    private String packageDescription;
    private PackageStatusEnum packageStatus;
    private String image;
    private LocalDateTime createAt;
    private Integer createBy;
    private LocalDateTime updateAt;
    private Integer updateBy;
}
