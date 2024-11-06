package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument;

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
public class UpdateHandoverDocumentRequest {
    private String handoverDescription;
    private String vehicle;
    private String destination;
    private String departure;
    private Float totalPrice;
}
