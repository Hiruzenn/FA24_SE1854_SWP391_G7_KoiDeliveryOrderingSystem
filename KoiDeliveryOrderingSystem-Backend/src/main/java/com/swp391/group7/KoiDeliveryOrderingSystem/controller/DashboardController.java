package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.DashboardResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping()
    public ResponseEntity<DashboardResponse> dashboard() {
        var result = dashboardService.dashboard();
        return ResponseEntity.ok(result);
    }
}
