package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CreateHealthCareDeliveryHistoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.HealthCareDeliveryHistoryDTO;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.HealthCareDeliveryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/healthcare-delivery-histories")
@RequiredArgsConstructor
public class HealthCareDeliveryHistoryController {

    private final HealthCareDeliveryHistoryService healthCareDeliveryHistoryService;

    // GET: Retrieve the list of healthcare delivery histories
    @GetMapping
    public ResponseEntity<ApiResponse<List<HealthCareDeliveryHistoryDTO>>> getAllHealthCareDeliveryHistories() {
        List<HealthCareDeliveryHistoryDTO> histories = healthCareDeliveryHistoryService.getListHealthCareDeliveryHistories();

        ApiResponse<List<HealthCareDeliveryHistoryDTO>> response = ApiResponse.<List<HealthCareDeliveryHistoryDTO>>builder()
                .code(200)
                .message("Healthcare delivery histories retrieved successfully")
                .result(histories)
                .build();

        return ResponseEntity.ok(response);
    }

    // GET: Retrieve a specific healthcare delivery history by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HealthCareDeliveryHistoryDTO>> getHealthCareDeliveryHistoryById(@PathVariable int id) {
        try {
            HealthCareDeliveryHistoryDTO historyDTO = healthCareDeliveryHistoryService.getHealthCareDeliveryHistoryById(id);
            ApiResponse<HealthCareDeliveryHistoryDTO> response = ApiResponse.<HealthCareDeliveryHistoryDTO>builder()
                    .code(200)
                    .message("Healthcare delivery history retrieved successfully")
                    .result(historyDTO)
                    .build();
            return ResponseEntity.ok(response);
        } catch (AppException e) {
            ApiResponse<HealthCareDeliveryHistoryDTO> response = ApiResponse.<HealthCareDeliveryHistoryDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // POST: Create a new healthcare delivery history
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<HealthCareDeliveryHistoryDTO>> createHealthCareDeliveryHistory(
            @RequestBody CreateHealthCareDeliveryHistoryRequest healthCareDeliveryHistoryRequest) {
        try {
            ApiResponse<HealthCareDeliveryHistoryDTO> createdHistoryResponse = healthCareDeliveryHistoryService.createHealthCareDeliveryHistory(healthCareDeliveryHistoryRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHistoryResponse);
        } catch (AppException e) {
            ApiResponse<HealthCareDeliveryHistoryDTO> response = ApiResponse.<HealthCareDeliveryHistoryDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<HealthCareDeliveryHistoryDTO> response = ApiResponse.<HealthCareDeliveryHistoryDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PUT: Update an existing healthcare delivery history by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<HealthCareDeliveryHistoryDTO>> updateHealthCareDeliveryHistory(
            @PathVariable Integer id,
            @RequestBody CreateHealthCareDeliveryHistoryRequest healthCareDeliveryHistoryRequest) {
        try {
            ApiResponse<HealthCareDeliveryHistoryDTO> updatedHistory = healthCareDeliveryHistoryService.updateHealthCareDeliveryHistory(id, healthCareDeliveryHistoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updatedHistory);
        } catch (AppException e) {
            ApiResponse<HealthCareDeliveryHistoryDTO> response = ApiResponse.<HealthCareDeliveryHistoryDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<HealthCareDeliveryHistoryDTO> response = ApiResponse.<HealthCareDeliveryHistoryDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE: Delete a healthcare delivery history by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHealthCareDeliveryHistory(@PathVariable Integer id) {
        try {
            ApiResponse<Void> response = healthCareDeliveryHistoryService.deleteHealthCareDeliveryHistory(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (AppException e) {
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
