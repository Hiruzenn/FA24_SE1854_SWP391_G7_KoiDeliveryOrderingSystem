package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceOrder;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthServiceOrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.HealthServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("view-by-order-id/{orderId}")
    public ApiResponse<List<HealthServiceOrderResponse>> getHealthServiceOrdersByOrderId(@PathVariable("orderId") Integer orderId){
        var result = healthServiceOrderService.getHealthServiceOrderByOrderId(orderId);
        return ApiResponse.<List<HealthServiceOrderResponse>>builder()
                .code(200)
                .message("Health Service Order View")
                .result(result)
                .build();
    }

    @PutMapping("delete/{orderId}/{healthServiceCategoryId}")
    public ApiResponse<HealthServiceOrderResponse> deleteHealServiceOrder(@PathVariable("orderId") Integer orderId,
                                                @PathVariable("healthServiceCategoryId") Integer healthServiceCategoryId){
         healthServiceOrderService.deleteHealthServiceOrder(orderId, healthServiceCategoryId);
        return ApiResponse.<HealthServiceOrderResponse>builder()
                .code(200)
                .message("Health Service Order Deleted")
                .build();
    }

}
