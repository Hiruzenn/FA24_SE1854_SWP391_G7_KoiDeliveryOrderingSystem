package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomsDeclarationRespone {
    private Integer id;


    private Orders orders;


    private String declarationNo;


    private LocalDateTime declarationDate;


    private String declaratonBy;


    private String referenceNo;


    private LocalDateTime referenceeDate;


    private String customsName;


    private String image;
    private SystemStatusEnum stautus;
}
