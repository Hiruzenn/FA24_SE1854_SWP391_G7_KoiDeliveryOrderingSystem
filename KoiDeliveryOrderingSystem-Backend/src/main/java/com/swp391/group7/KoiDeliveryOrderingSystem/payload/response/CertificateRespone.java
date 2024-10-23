package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateRespone {
    private int id;
    private String certificateName;
    private String certificateDescription;
    private int orderId;
    private String health;
    private String origin;
    private String award;
    private String image;
    private SystemStatusEnum  stautus;
}
