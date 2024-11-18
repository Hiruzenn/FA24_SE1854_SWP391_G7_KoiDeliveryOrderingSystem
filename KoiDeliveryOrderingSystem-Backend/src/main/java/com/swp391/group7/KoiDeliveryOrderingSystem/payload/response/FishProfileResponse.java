package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FishProfileResponse {
    private Integer id;
    private Integer orderId;
    private String species;
    private String name;
    private String description;
    private String sex;
    private String size;
    private Integer age;
    private String origin;
    private Float weight;
    private String color;
    private String image;
    private LocalDateTime createAt;
    private Integer createBy;
    private LocalDateTime updateAt;
    private Integer updateBy;
}
