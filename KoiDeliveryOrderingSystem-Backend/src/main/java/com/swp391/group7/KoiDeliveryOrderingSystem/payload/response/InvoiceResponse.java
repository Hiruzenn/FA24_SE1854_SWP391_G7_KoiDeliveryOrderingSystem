package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.InvoiceStatusEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponse {
    private Integer id;
    private String invoiceNo;
    private Integer orderId;
    private Integer userId;
    private String addressStore;
    private String addressCustomer;
    private Float amount;
    private Float vat;
    private Float vatAmount;
    private Float totalAmount;
    private InvoiceStatusEnum status;
}
