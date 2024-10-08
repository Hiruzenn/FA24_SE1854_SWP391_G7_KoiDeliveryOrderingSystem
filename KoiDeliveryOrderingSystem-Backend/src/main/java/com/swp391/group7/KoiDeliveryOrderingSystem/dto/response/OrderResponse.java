package com.swp391.group7.KoiDeliveryOrderingSystem.dto.response;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
   private String orderCode;
   private Integer customers;
   private String deliveryMethod;
   private LocalDateTime orderDate;
   private LocalDateTime estimateDeliveryDate;
   private String destination;
   private String departure;
   private float distance;
   private String phone;
   private float amount;
   private float vat;
   private float vatAmount;
   private float totalAmount;
   private LocalDateTime createAt;
   private int createBy;
   private LocalDateTime updateAt;
   private int updateBy;
   private SystemStatusEnum status;
}
