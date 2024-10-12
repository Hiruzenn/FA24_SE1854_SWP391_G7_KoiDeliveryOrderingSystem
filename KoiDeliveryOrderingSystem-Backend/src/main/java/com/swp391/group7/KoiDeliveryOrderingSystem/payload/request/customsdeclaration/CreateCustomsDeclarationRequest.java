package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.customsdeclaration;

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
    private String declarationNo;
    private LocalDateTime declarationDate;
    private String declaratonBy;
    private String referenceNo;
    private LocalDateTime referenceeDate;
    private String customsName;
    private String image;
}
