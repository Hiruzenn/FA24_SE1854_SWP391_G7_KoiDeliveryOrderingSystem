package com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDTO {
    private int id;
    private String certificateName;
    private String certificateDescription;
    private int orderId;
    private String health;
    private String origin;
    private String award;
    private String image;
}
