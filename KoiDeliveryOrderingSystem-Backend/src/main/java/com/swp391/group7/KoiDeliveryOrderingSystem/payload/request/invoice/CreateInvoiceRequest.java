package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.invoice;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthCareDeliveryHistory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
    private String invoiceNo;

    private Orders orders;

    private List<Package> packages;

    private List<HealthCareDeliveryHistory> healCareDeliveryHistories;

    private Users users;

    private Integer healthCareDeliveryHistoryId;

    private String invoiceDescription;

    private String staff;

    private String vehicle;

    private String destination;

    private String depature;

    private Float totalPrice;
}
