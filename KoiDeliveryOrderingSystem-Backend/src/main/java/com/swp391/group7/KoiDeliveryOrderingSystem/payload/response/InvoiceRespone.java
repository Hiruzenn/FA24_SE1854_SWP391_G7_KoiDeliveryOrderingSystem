package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthCareDeliveryHistory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceRespone {

    private Integer id;


    private String invoiceNo;


    private String staff;


    private Orders orders;


    private Users users;


    private List<HealthCareDeliveryHistory> healCareDeliveryHistories;

    private LocalDateTime date;


    private String addressStore;


    private String addressCustomer;


    private Float vat;


    private Float amount;


    private Float totalAmount;


    private LocalDateTime createAt;


    private Integer createBy;


    private LocalDateTime updateAt;


    private Integer updateBy;


    private SystemStatusEnum status;
}
