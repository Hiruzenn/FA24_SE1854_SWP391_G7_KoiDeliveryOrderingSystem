package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.dashboard.CustomerDashboardResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.dashboard.DeliveryStaffDashboardResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.dashboard.ManagerDashboardResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.dashboard.SaleStaffDashboardResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/manager")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> manager() {
        var result = dashboardService.manager();
        return ResponseEntity.ok(ApiResponse.<ManagerDashboardResponse>builder()
                .code(200)
                .message("Dashboard Manager")
                .result(result)
                .build());
    }

    @GetMapping("/customer")
    public ResponseEntity<ApiResponse<CustomerDashboardResponse>> customer() {
        var result = dashboardService.customer();
        return ResponseEntity.ok(ApiResponse.<CustomerDashboardResponse>builder()
                .code(200)
                .message("Dashboard Customer")
                .result(result)
                .build());
    }

    @GetMapping("/delivery-staff")
    public ResponseEntity<ApiResponse<DeliveryStaffDashboardResponse>> deliveryStaff() {
        var result = dashboardService.deliveryStaff();
        return ResponseEntity.ok(ApiResponse.<DeliveryStaffDashboardResponse>builder()
                .code(200)
                .message("Dashboard Delivery Staff")
                .result(result)
                .build());
    }

    @GetMapping("/sale-staff")
    public ResponseEntity<ApiResponse<SaleStaffDashboardResponse>> saleStaff() {
        var result = dashboardService.saleStaff();
        return ResponseEntity.ok(ApiResponse.<SaleStaffDashboardResponse>builder()
                .code(200)
                .message("Dashboard Sale Staff")
                .result(result)
                .build());
    }
}
