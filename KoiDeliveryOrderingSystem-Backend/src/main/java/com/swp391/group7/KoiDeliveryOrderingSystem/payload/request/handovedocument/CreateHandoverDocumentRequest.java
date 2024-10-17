package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthCareDeliveryHistory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import jakarta.persistence.*;
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



    private Users users;


    private Package packages;


    private Orders orders;


    private List<HealthCareDeliveryHistory> healthCareDeliveryHistory;


    private String handoverNo;


    private String staff;


    private String handoverDescription;


    private String vehicle;


    private String destination;


    private String departure;


    private String totalPrice;

}
