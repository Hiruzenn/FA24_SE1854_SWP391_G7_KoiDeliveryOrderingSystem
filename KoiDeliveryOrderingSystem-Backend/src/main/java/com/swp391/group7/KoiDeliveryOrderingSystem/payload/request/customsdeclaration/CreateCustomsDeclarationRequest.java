package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.customsdeclaration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomsDeclarationRequest {
    @NotBlank(message = "Declaration No is required")
    private String customsName;

    @NotBlank(message = "Declaration No is required")
    private String declarationNo;

    @NotNull(message = "Declaration No is required")
    private LocalDateTime declarationDate;

    @NotBlank(message = "Declaration No is required")
    private String declarationBy;

    @NotBlank(message = "Declaration No is required")
    private String referenceNo;
    
    @NotNull(message = "Declaration No is required")
    private LocalDateTime referenceDate;

    @NotBlank(message = "Declaration No is required")
    private String image;
}
