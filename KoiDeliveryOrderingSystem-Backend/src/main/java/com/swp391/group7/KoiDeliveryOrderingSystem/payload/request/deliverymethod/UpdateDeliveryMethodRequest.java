package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod;


import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDeliveryMethodRequest {

    private String delivery_name;


}
