package com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HandoverDocumentDTO {

    private Integer id;

    private Users users;

    private Orders orders;

    private String handoverNo;

    private String staff;

    private String handoverDescription;

    private String vehicle;
    private String destination;

    private String departure;

    private Float totalPrice;

}