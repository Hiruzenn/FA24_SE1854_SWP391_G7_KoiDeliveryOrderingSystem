package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth.CreateCheckingKoiHealthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth.UpdateCheckingKoiHealthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CheckingKoiHealthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.CheckingKoiHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("checking-koi-health")
public class CheckKoiHealthController {
    @Autowired
    private CheckingKoiHealthService checkingKoiHealthService;

    @PostMapping("create/{orderDetailId}/{packageId}")
    public ResponseEntity<ApiResponse<CheckingKoiHealthResponse>> createCheckingKoiHealth(@RequestBody CreateCheckingKoiHealthRequest createCheckingKoiHealthRequest) {
        var result = checkingKoiHealthService.createCheckingKoiHealth(createCheckingKoiHealthRequest);
        return ResponseEntity.ok(ApiResponse.<CheckingKoiHealthResponse>builder()
                .code(200)
                .message("Checking Koi Health Created")
                .result(result)
                .build());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<CheckingKoiHealthResponse>> updateCheckingKoiHealth(
            @PathVariable("id") Integer id,
            @RequestBody UpdateCheckingKoiHealthRequest updateCheckingKoiHealthRequest) {
        var result = checkingKoiHealthService.updateCheckingKoiHealth(id, updateCheckingKoiHealthRequest);
        return ResponseEntity.ok(ApiResponse.<CheckingKoiHealthResponse>builder()
                .code(200)
                .message("Checking Koi Health Updated")
                .result(result)
                .build());
    }

    @PutMapping("delete/{id}")
    public ResponseEntity<ApiResponse<CheckingKoiHealthResponse>> deleteCheckingKoiHealth(@PathVariable("id") Integer id) {
        var result = checkingKoiHealthService.deleteCheckingKoiHealth(id);
        return ResponseEntity.ok(ApiResponse.<CheckingKoiHealthResponse>builder()
                .code(200)
                .message("Checking Koi Health Updated")
                .result(result)
                .build());
    }

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<CheckingKoiHealthResponse>>> getAllCheckingKoiHealth() {
        var result = checkingKoiHealthService.getAllCheckingKoiHealth();
        return ResponseEntity.ok(ApiResponse.<List<CheckingKoiHealthResponse>>builder()
                .code(200)
                .message("View All Checking Koi Health")
                .result(result)
                .build());
    }

    @GetMapping("view-one/{packageId}")
    public ResponseEntity<ApiResponse<List<CheckingKoiHealthResponse>>> viewCheckingKoiHealthByPackage(@PathVariable Integer packageId) {
        var result = checkingKoiHealthService.viewCheckingKoiHealthByPackage(packageId);
        return ResponseEntity.ok(ApiResponse.<List<CheckingKoiHealthResponse>>builder()
                .code(200)
                .message("View All Checking Koi Health")
                .result(result)
                .build());
    }

    @GetMapping("view-by-order-detail-id/{orderDetailId}")
    public ResponseEntity<ApiResponse<List<CheckingKoiHealthResponse>>> viewCheckingKoiHealthByOrderDetail(@PathVariable Integer orderDetailId) {
        var result = checkingKoiHealthService.viewCheckingKoiHealthByOrderDetail(orderDetailId);
        return ResponseEntity.ok(ApiResponse.<List<CheckingKoiHealthResponse>>builder()
                .code(200)
                .message("View All Checking Koi Health")
                .result(result)
                .build());
    }
}
