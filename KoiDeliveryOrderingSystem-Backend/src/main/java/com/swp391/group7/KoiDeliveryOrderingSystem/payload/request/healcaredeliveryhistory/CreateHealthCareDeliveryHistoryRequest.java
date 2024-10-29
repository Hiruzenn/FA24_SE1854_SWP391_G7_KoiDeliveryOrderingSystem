package com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.DeliveryStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HandoverDocument;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Invoice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHealthCareDeliveryHistoryRequest {
    private Integer invoiceId;
    private Integer handoverDocumentId;
    private String route;
    private String healthDescription;
    private String eatingDescription;
    private DeliveryStatusEnum deliveryStatus;

}
