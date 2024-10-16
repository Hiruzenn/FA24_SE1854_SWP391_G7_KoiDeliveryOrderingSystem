package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateDeliveryMethodRequest {

    private String delivery_name;


}
