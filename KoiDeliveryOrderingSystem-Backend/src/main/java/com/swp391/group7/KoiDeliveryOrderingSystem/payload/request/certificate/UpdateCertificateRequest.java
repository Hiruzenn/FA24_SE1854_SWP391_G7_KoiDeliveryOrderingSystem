package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCertificateRequest {
    @NotBlank(message =  "Certificate Name is required")
    private String name;

    @NotBlank(message =  "Certificate Description Name is required")
    private String species;

    @NotBlank(message =  "Award Name is required")
    private String award;

    @NotBlank(message = "Sex is required")
    private String sex;

    @NotNull(message = "Size is required")
    private Integer size;

    @NotNull(message = "Age is required")
    private Integer age;

    @NotBlank(message =  "Image Name is required")
    private String image;

}
