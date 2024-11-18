package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth.CreateCheckingKoiHealthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth.UpdateCheckingKoiHealthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CheckingKoiHealthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.CheckingKoiHealthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("checking-koi-health")
@Tag(name = "Checking Koi Health")
public class CheckKoiHealthController {
    @Autowired
    private CheckingKoiHealthService checkingKoiHealthService;

    @PostMapping("create/{fishProfileId}")
    public ResponseEntity<ApiResponse<CheckingKoiHealthResponse>> createCheckingKoiHealth(@Valid @PathVariable("fishProfileId") Integer id,
                                                                                          @RequestBody CreateCheckingKoiHealthRequest request) {
        var result = checkingKoiHealthService.createCheckingKoiHealth(id, request);
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

    @GetMapping("view-by-fish-profile/{fishProfileId}")
    public ResponseEntity<ApiResponse<List<CheckingKoiHealthResponse>>> viewCheckingKoiHealthByOrderDetail(@PathVariable Integer fishProfileId) {
        var result = checkingKoiHealthService.viewCheckingKoiHealthByFishProfile(fishProfileId);
        return ResponseEntity.ok(ApiResponse.<List<CheckingKoiHealthResponse>>builder()
                .code(200)
                .message("View All Checking Koi Health")
                .result(result)
                .build());
    }

    @GetMapping("existed-checking-koi-heath/{fishProfileId}")
    public ResponseEntity<ApiResponse<Boolean>> existedCheckingKoiHealth(@PathVariable("fishProfileId") Integer FishProfileId) {
        var result = checkingKoiHealthService.existedCheckingKoiHealth(FishProfileId);
        return ResponseEntity.ok(ApiResponse.<Boolean>builder()
                .code(200)
                .message("Existed Checking Koi Health")
                .result(result)
                .build());
    }
}
