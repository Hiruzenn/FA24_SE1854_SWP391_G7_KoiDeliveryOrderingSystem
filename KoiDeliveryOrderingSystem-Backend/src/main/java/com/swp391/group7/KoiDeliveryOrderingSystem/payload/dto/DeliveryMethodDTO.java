package com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryMethodDTO {

    private Integer id;

    private String delivery_name;

}
