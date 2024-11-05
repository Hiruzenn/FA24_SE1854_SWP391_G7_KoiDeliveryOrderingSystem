package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceOrder;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthserviceorder.CreateHealthServiceOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthserviceorder.UpdateHealthServiceOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthServiceOrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.HealthServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("health-service-order")
@RequiredArgsConstructor
public class HealthServiceOrderController {
    @Autowired
    private HealthServiceOrderService healthServiceOrderService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<HealthServiceOrderResponse>> createHealthServiceOrder(@RequestBody CreateHealthServiceOrderRequest createHealthServiceOrderRequest) {
        var result = healthServiceOrderService.createHealthServiceOrder(createHealthServiceOrderRequest);
        return ResponseEntity.ok(ApiResponse.<HealthServiceOrderResponse>builder()
                .code(200)
                .message("Health Service Order Created")
                .result(result)
                .build());
    }

    @GetMapping("view-by-order-id/{orderId}")
    public ResponseEntity<ApiResponse<List<HealthServiceOrderResponse>>> getHealthServiceOrdersByOrderId(@PathVariable("orderId") Integer orderId) {
        var result = healthServiceOrderService.getHealthServiceOrderByOrderId(orderId);
        return ResponseEntity.ok(ApiResponse.<List<HealthServiceOrderResponse>>builder()
                .code(200)
                .message("Health Service Order View")
                .result(result)
                .build());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<HealthServiceOrderResponse>> updateHealServiceOrder(
            @PathVariable("id") Integer id,
            @RequestBody UpdateHealthServiceOrderRequest updateHealthServiceOrder) {
        var result = healthServiceOrderService.updateHealthServiceOrder(id, updateHealthServiceOrder);
        return ResponseEntity.ok(ApiResponse.<HealthServiceOrderResponse>builder()
                .code(200)
                .message("Health Service Order Deleted")
                .result(result)
                .build());
    }

    @PutMapping("delete/{id}")
    public ResponseEntity<ApiResponse<HealthServiceOrderResponse>> deleteHealServiceOrder(@PathVariable("id") Integer id) {
        var result = healthServiceOrderService.deleteHealthServiceOrder(id);
        return ResponseEntity.ok(ApiResponse.<HealthServiceOrderResponse>builder()
                .code(200)
                .message("Health Service Order Deleted")
                .result(result)
                .build());
    }

}
