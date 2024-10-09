package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckKoiHealthResponse {
    private Integer id;
    private Integer orderId;
    private String healthStatusDescription;
    private Float weight;
    private String color;
    private String type;
    private Integer age;
    private Float price;
    private int  orderDetail;
    private int packages;
}
