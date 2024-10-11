package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceOrder;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthServiceOrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.HealthServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health-service-order")
@RequiredArgsConstructor
public class HealthServiceOrderController {
    @Autowired
    private HealthServiceOrderService healthServiceOrderService;

    @PostMapping("create/{orderId}/{healthServiceCategoryId}")
    public ApiResponse<HealthServiceOrderResponse> createHealthServiceOrder(@PathVariable("orderId") Integer orderId,
                                                                             @PathVariable("healthServiceCategoryId") Integer healthServiceCategoryId){
        var result = healthServiceOrderService.createHealthServiceOrder(orderId, healthServiceCategoryId);
        return ApiResponse.<HealthServiceOrderResponse>builder()
                .code(200)
                .message("Health Service Order Created")
                .result(result)
                .build();
    }
}
