package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth.CreateCheckingKoiHealthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CheckingKoiHealthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.CheckingKoiHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("checking-koi-health")
public class CheckKoiHealController {
    @Autowired
    private CheckingKoiHealthService checkingKoiHealthService;

    @PostMapping("create/{orderDetailId}/{packageId}")
    public ApiResponse<CheckingKoiHealthResponse> createCheckingKoiHealth(@PathVariable("orderDetailId") Integer orderDetailId,
                                                                          @PathVariable("packageId") Integer packageId,
                                                                          @RequestBody CreateCheckingKoiHealthRequest createCheckingKoiHealthRequest){
    var result = checkingKoiHealthService.createCheckingKoiHealth(orderDetailId, packageId, createCheckingKoiHealthRequest);
    return ApiResponse.<CheckingKoiHealthResponse>builder()
            .code(200)
            .message("Checking Koi Health Created")
            .result(result)
            .build();
    }

    @GetMapping("view-all")
    public ApiResponse<List<CheckingKoiHealthResponse>> getAllCheckingKoiHealth(){
        var result = checkingKoiHealthService.getAllCheckingKoiHealth();
        return ApiResponse.<List<CheckingKoiHealthResponse>>builder()
                .code(200)
                .message("View All Checking Koi Health")
                .result(result)
                .build();
    }

    @GetMapping("view-by-package-id/{packageId}")
    public ApiResponse<List<CheckingKoiHealthResponse>> viewCheckingKoiHealthByPackage(@PathVariable Integer packageId){
        var result = checkingKoiHealthService.viewCheckingKoiHealthByPackage(packageId);
        return ApiResponse.<List<CheckingKoiHealthResponse>>builder()
                .code(200)
                .message("View All Checking Koi Health")
                .result(result)
                .build();
    }
    @GetMapping("view-by-order-detail-id/{orderDetailId}")
    public ApiResponse<List<CheckingKoiHealthResponse>> viewCheckingKoiHealthByOrderDetail(@PathVariable Integer orderDetailId){
        var result = checkingKoiHealthService.viewCheckingKoiHealthByOrderDetail(orderDetailId);
        return ApiResponse.<List<CheckingKoiHealthResponse>>builder()
                .code(200)
                .message("View All Checking Koi Health")
                .result(result)
                .build();
    }
}
