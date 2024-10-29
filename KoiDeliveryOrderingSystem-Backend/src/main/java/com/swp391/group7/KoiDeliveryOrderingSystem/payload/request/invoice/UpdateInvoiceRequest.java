package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
    private LocalDateTime date;
    private String addressStore;
    private String addressCustomer;
    private Float vat;
    private Float amount;
    private Float totalAmount;
}
