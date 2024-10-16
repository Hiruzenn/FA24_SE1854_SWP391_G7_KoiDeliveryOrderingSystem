package com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private Integer id;

    private String invoiceNo;

    private int orders;

    private Users users;

    private Integer healthCareDeliveryHistoryId;

    private String invoiceDescription;

    private String staff;

    private String vehicle;

    private String destination;

    private String depature;

    private Float totalPrice;

}
