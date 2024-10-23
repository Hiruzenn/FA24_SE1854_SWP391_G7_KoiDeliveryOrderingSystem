package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class HandoverDocumentResponse {
    String handoverNo;
    Integer userId;
    Integer orderId;
    String staff;
    String handoverDescription;
    String vehicle;
    String destination;
    String departure;
    Float totalPrice;
    LocalDateTime createAt;
    Integer createBy;
    LocalDateTime updateAt;
    Integer updateBy;
    SystemStatusEnum status;
}