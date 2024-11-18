package com.swp391.group7.KoiDeliveryOrderingSystem.controller;


import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod.CreateDeliveryMethodRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod.UpdateDeliveryMethodRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.DeliveryMethodResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.DeliveryMethodService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery-method")
@RequiredArgsConstructor
@Tag(name = "Delivery Method")
public class DeliveryMethodController {
    @Autowired
    private final DeliveryMethodService deliveryMethodService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<DeliveryMethodResponse>> createDeliveryMethod(@Valid @RequestBody CreateDeliveryMethodRequest createDeliveryMethodRequest) {
        var result = deliveryMethodService.createDeliveryMethod(createDeliveryMethodRequest);
        return ResponseEntity.ok(ApiResponse.<DeliveryMethodResponse>builder()
                .code(200)
                .message("Delivery Method Created")
                .result(result)
                .build());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<DeliveryMethodResponse>> updateDeliveryMethod(@Valid @PathVariable int id, @RequestBody UpdateDeliveryMethodRequest updateDeliveryMethodRequest) {
        var result = deliveryMethodService.updateDeliveryMethod(id, updateDeliveryMethodRequest);
        return ResponseEntity.ok(ApiResponse.<DeliveryMethodResponse>builder()
                .code(200)
                .message("Delivery Method Updated")
                .result(result)
                .build());
    }

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<DeliveryMethodResponse>>> viewAllDeliveryMethod() {
        var result = deliveryMethodService.viewAllDeliveryMethods();
        return ResponseEntity.ok(ApiResponse.<List<DeliveryMethodResponse>>builder()
                .code(200)
                .message("Delivery Method Viewed")
                .result(result)
                .build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDeliveryMethod(@PathVariable int id) {
        deliveryMethodService.deleteDeliveryMethod(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Delivery Method Deleted")
                .build());
    }
}
