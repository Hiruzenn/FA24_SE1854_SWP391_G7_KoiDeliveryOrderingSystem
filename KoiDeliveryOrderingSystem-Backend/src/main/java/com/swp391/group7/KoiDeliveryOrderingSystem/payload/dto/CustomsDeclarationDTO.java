package com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomsDeclarationDTO {

    private Integer id;


    private Orders orders;


    private String declarationNo;


    private LocalDateTime declarationDate;


    private String declaratonBy;


    private String referenceNo;


    private LocalDateTime referenceeDate;


    private String customsName;


    private String image;

}
