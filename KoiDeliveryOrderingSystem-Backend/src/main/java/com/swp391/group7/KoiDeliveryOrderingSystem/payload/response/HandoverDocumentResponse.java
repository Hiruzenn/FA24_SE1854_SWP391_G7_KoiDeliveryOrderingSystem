package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthCareDeliveryHistory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HandoverDocumentResponse {
    private int id;
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

    private LocalDateTime createAt;


    private Integer createBy;


    private LocalDateTime updateAt;


    private Integer updateBy;


    private SystemStatusEnum status;
}