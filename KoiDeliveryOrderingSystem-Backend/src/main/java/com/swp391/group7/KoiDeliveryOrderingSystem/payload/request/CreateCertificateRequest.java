package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCertificateRequest {
    private String certificateName;
    private String certificateDescription;
    private int orderId;
    private String health;
    private String origin;
    private String award;
    private String image;
}
