package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.invoice;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthCareDeliveryHistory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {


    private String invoiceNo;


    private String staff;


    private int orders;


    private int users;


    private int healCareDeliveryHistories;

    private LocalDateTime date;


    private String addressStore;


    private String addressCustomer;


    private Float vat;


    private Float amount;


    private Float totalAmount;

}
