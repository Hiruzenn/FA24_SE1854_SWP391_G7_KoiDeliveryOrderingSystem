package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CreateHealthCareDeliveryHistoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.HealthCareDeliveryHistoryDTO;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthCareDeliveryHistoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.HealthCareDeliveryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/healthcare-delivery-histories")
@RequiredArgsConstructor
public class HealthCareDeliveryHistoryController {

    @Autowired
    HealthCareDeliveryHistoryService healthCareDeliveryHistoryService;

    // GET: Retrieve the list of healthcare delivery histories
    @GetMapping
    public ResponseEntity<ApiResponse<List<HealthCareDeliveryHistoryResponse>>> getAllHealthCareDeliveryHistories() {
        List<HealthCareDeliveryHistoryResponse> histories = healthCareDeliveryHistoryService.getListHealthCareDeliveryHistories();
        ApiResponse<List<HealthCareDeliveryHistoryResponse>> response = ApiResponse.<List<HealthCareDeliveryHistoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Healthcare delivery histories retrieved successfully")
                .result(histories)
                .build();
        return ResponseEntity.ok(response);
    }

    // Retrieve a specific healthcare delivery history by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HealthCareDeliveryHistoryResponse>> getHealthCareDeliveryHistoryById(@PathVariable int id) {
        HealthCareDeliveryHistoryResponse historyResponse = healthCareDeliveryHistoryService.getHealthCareDeliveryHistoryById(id);
        ApiResponse<HealthCareDeliveryHistoryResponse> response = ApiResponse.<HealthCareDeliveryHistoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Healthcare delivery history retrieved successfully")
                .result(historyResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    // Create a new healthcare delivery history
    @PostMapping
    public ResponseEntity<ApiResponse<HealthCareDeliveryHistoryResponse>> createHealthCareDeliveryHistory(
            @RequestBody CreateHealthCareDeliveryHistoryRequest request,
            @RequestParam int handoverDocumentId,
            @RequestParam int invoiceId) {

        HealthCareDeliveryHistoryResponse createdHistory = healthCareDeliveryHistoryService.createHealthCareDeliveryHistory(request, handoverDocumentId, invoiceId);

        ApiResponse<HealthCareDeliveryHistoryResponse> response = ApiResponse.<HealthCareDeliveryHistoryResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Healthcare delivery history created successfully")
                .result(createdHistory)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update an existing healthcare delivery history by ID
    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<HealthCareDeliveryHistoryResponse>> updateHealthCareDeliveryHistory(
            @PathVariable Integer id,
            @RequestBody CreateHealthCareDeliveryHistoryRequest request) {

        HealthCareDeliveryHistoryResponse updatedHistory = healthCareDeliveryHistoryService.updateHealthCareDeliveryHistory(id, request);

        ApiResponse<HealthCareDeliveryHistoryResponse> response = ApiResponse.<HealthCareDeliveryHistoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Healthcare delivery history updated successfully")
                .result(updatedHistory)
                .build();
        return ResponseEntity.ok(response);
    }

    // Delete a healthcare delivery history by ID
    @PutMapping("delete/{id}")
    public ResponseEntity<ApiResponse<HealthCareDeliveryHistoryResponse>> removeHealthCareDeliveryHistory(@PathVariable Integer id) {
        HealthCareDeliveryHistoryResponse deletedHistory = healthCareDeliveryHistoryService.removeHealthCareDeliveryHistory(id);

        ApiResponse<HealthCareDeliveryHistoryResponse> response = ApiResponse.<HealthCareDeliveryHistoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Healthcare delivery history deleted successfully")
                .result(deletedHistory)
                .build();
        return ResponseEntity.ok(response);
    }
}
