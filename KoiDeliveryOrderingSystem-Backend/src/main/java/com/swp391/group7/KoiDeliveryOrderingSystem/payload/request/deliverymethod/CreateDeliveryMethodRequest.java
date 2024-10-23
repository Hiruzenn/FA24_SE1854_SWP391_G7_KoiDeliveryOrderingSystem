package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDeliveryMethodRequest {

    private String delivery_name;


}
