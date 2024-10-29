package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCertificateRequest {
    @NotBlank(message =  "Certificate Name is required")
    private String certificateName;

    @NotBlank(message =  "Certificate Description Name is required")
    private String certificateDescription;

    @NotBlank(message =  "Health Name is required")
    private String health;

    @NotBlank(message =  "Origin Name is required")
    private String origin;

    @NotBlank(message =  "Award Name is required")
    private String award;

    @NotBlank(message =  "Image Name is required")
    private String image;
}
