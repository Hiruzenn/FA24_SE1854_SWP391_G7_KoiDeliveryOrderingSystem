package com.swp391.group7.KoiDeliveryOrderingSystem.controller;


import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.DashboardResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.DashboardService;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.CalculateMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private CalculateMoney calculateMoney;

    @GetMapping()
    public ResponseEntity<DashboardResponse> dashboard() {
        var result = dashboardService.dashboard();
        return ResponseEntity.ok(result);
    }
    @PostMapping("test")
    public Float test(@RequestParam("start") String start, @RequestParam("end") String end) {
        return calculateMoney.calculateDistance(start, end);
    }
}
