package com.swp391.group7.KoiDeliveryOrderingSystem.controller;


import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod.CreateDeliveryMethodRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod.UpdateDeliveryMethodRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.DeliveryMethodResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.DeliveryMethodService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery-method")
@RequiredArgsConstructor
public class DeliveryMethodController {
    @Autowired
    private final DeliveryMethodService deliveryMethodService;

    @PostMapping("create")
    public ApiResponse<DeliveryMethodResponse> createDeliveryMethod(@RequestBody CreateDeliveryMethodRequest createDeliveryMethodRequest) {
        var result = deliveryMethodService.createDeliveryMethod(createDeliveryMethodRequest);
        return ApiResponse.<DeliveryMethodResponse>builder()
                .code(200)
                .message("Delivery Method Created")
                .result(result)
                .build();
    }

    @PutMapping("update/{id}")
    public ApiResponse<DeliveryMethodResponse> updateDeliveryMethod(@PathVariable int id, @RequestBody UpdateDeliveryMethodRequest updateDeliveryMethodRequest) {
        var result = deliveryMethodService.updateDeliveryMethod(id, updateDeliveryMethodRequest);
        return ApiResponse.<DeliveryMethodResponse>builder()
                .code(200)
                .message("Delivery Method Updated")
                .result(result)
                .build();
    }

    @GetMapping("view-all")
    public ApiResponse<List<DeliveryMethodResponse>> viewAllDeliveryMethod() {
        var result = deliveryMethodService.viewAllDeliveryMethods();
        return ApiResponse.<List<DeliveryMethodResponse>>builder()
                .code(200)
                .message("Delivery Method Viewed")
                .result(result)
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<DeliveryMethodResponse> deleteDeliveryMethod(@PathVariable int id) {
        var result = deliveryMethodService.deleteDeliveryMethod(id);
        return ApiResponse.<DeliveryMethodResponse>builder()
                .code(200)
                .message("Delivery Method Deleted")
                .result(result)
                .build();
    }
}
