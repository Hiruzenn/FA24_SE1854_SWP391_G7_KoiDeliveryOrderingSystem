package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HandoverStatusEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HandoverDocumentResponse {
    private Integer id;
    private Integer userId;
    private Integer orderId;
    private String handoverNo;
    private String handoverDescription;
    private String vehicle;
    private String destination;
    private String departure;
    private Float totalPrice;
    private String image;
    private LocalDateTime createAt;
    private Integer createBy;
    private LocalDateTime updateAt;
    private Integer updateBy;
    private HandoverStatusEnum handoverStatusEnum;
}