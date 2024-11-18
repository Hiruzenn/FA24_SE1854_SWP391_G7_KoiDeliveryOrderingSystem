package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CreateHealthCareDeliveryHistoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthCareDeliveryHistoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.HealthCareDeliveryHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("healthcare-delivery-histories")
@RequiredArgsConstructor
@Tag(name = "Health Care Delivery History")
public class HealthCareDeliveryHistoryController {

    @Autowired
    HealthCareDeliveryHistoryService healthCareDeliveryHistoryService;

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<HealthCareDeliveryHistoryResponse>>> viewAll() {
        var result = healthCareDeliveryHistoryService.viewAll();
        return ResponseEntity.ok(ApiResponse.<List<HealthCareDeliveryHistoryResponse>>builder()
                .code(200)
                .message("Healthcare delivery histories retrieved successfully")
                .result(result)
                .build());
    }

    @GetMapping("view-one/{id}")
    public ResponseEntity<ApiResponse<HealthCareDeliveryHistoryResponse>> viewOne(@PathVariable int id) {
        var result = healthCareDeliveryHistoryService.viewById(id);
        return ResponseEntity.ok(ApiResponse.<HealthCareDeliveryHistoryResponse>builder()
                .code(200)
                .message("Healthcare delivery history retrieved successfully")
                .result(result)
                .build());
    }

    @GetMapping("view-by-handover/{handoverDocumentId}")
    public ResponseEntity<ApiResponse<List<HealthCareDeliveryHistoryResponse>>> viewByHandover(@PathVariable("handoverDocumentId") Integer handoverDocumentId) {
        var result = healthCareDeliveryHistoryService.viewByHandoverDocument(handoverDocumentId);
        return ResponseEntity.ok(ApiResponse.<List<HealthCareDeliveryHistoryResponse>>builder()
                .code(200)
                .message("View By Handover Documents")
                .result(result)
                .build());
    }

    @PostMapping("create/{orderId}")
    public ResponseEntity<ApiResponse<HealthCareDeliveryHistoryResponse>> createHealthCareDeliveryHistory(
            @PathVariable("orderId") Integer orderId,
            @RequestBody CreateHealthCareDeliveryHistoryRequest request) {
        var result = healthCareDeliveryHistoryService.create(orderId, request);
        return ResponseEntity.ok(ApiResponse.<HealthCareDeliveryHistoryResponse>builder()
                .code(200)
                .message("Healthcare delivery history created successfully")
                .result(result)
                .build());

    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<HealthCareDeliveryHistoryResponse>> updateHealthCareDeliveryHistory(
            @PathVariable Integer id,
            @RequestBody CreateHealthCareDeliveryHistoryRequest request) {

        var result = healthCareDeliveryHistoryService.update(id, request);

        return ResponseEntity.ok(ApiResponse.<HealthCareDeliveryHistoryResponse>builder()
                .code(200)
                .message("Healthcare delivery history updated successfully")
                .result(result)
                .build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> removeHealthCareDeliveryHistory(@PathVariable Integer id) {
        healthCareDeliveryHistoryService.remove(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Healthcare delivery history deleted successfully")
                .build());
    }
}
