package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

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
   private Integer id;
   private String orderCode;
   private String deliveryMethod;
   private LocalDateTime orderDate;
   private LocalDateTime estimateDeliveryDate;
   private LocalDateTime receivingDate;
   private String destination;
   private String departure;
   private Float distance;
   private String phone;
   private Float amount;
   private Float vat;
   private Float vatAmount;
   private Float totalAmount;
   private LocalDateTime createAt;
   private Integer createBy;
   private LocalDateTime updateAt;
   private Integer updateBy;
   private SystemStatusEnum status;
}
