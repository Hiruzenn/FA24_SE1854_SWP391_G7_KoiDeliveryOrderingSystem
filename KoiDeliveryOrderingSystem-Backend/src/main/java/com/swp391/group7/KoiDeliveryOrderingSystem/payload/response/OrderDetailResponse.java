package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    private Integer id;
    private Integer fishProfileId;
    private Integer orderId;
    private Integer quantity;
    private Float unitPrice;
    private Float amount;
    private LocalDateTime receivingDate;
    private LocalDateTime createAt;
    private Integer createBy;
    private LocalDateTime updateAt;
    private Integer updateBy;
}
