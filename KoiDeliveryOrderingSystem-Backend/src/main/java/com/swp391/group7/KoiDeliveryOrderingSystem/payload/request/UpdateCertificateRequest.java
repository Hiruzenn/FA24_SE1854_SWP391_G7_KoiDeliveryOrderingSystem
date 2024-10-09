package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCertificateRequest {
    private int id;
    private String certificateName;
    private String certificateDescription;
    private int orderId;
    private String health;
    private String origin;
    private String award;
    private String image;
}
