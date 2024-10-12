package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Customers;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthCareDeliveryHistory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHandoverDocumentRequest {


    private Customers customers;


    private Orders orders;




    private String handoverNo;

    private String staff;

    private String handoverDescription;

    private String vehicle;
    private String destination;

    private String departure;

    private String totalPrice;
}
