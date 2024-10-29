package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthyservicecategory.CreateHealthServiceCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthyservicecategory.UpdateHealthServiceCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthServiceCategoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.HealthServiceCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("heal-service-category")
@RequiredArgsConstructor
public class HealthServiceCategoryController {
    @Autowired
    private HealthServiceCategoryService healthServiceCategoryService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<HealthServiceCategoryResponse>> createHealthyServiceCategory(@Valid @RequestBody CreateHealthServiceCategoryRequest createHealthServiceCategoryRequest) {
        var result = healthServiceCategoryService.createHealthyServiceCategory(createHealthServiceCategoryRequest);
        return ResponseEntity.ok(ApiResponse.<HealthServiceCategoryResponse>builder()
                .code(200)
                .message("Healthy Service Category Created")
                .result(result)
                .build());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<HealthServiceCategoryResponse>> updateHealthServiceCategory(@Valid @PathVariable int id, @RequestBody UpdateHealthServiceCategoryRequest updateHealthServiceCategoryRequest) {
        var result = healthServiceCategoryService.updateHealthServiceCategory(id, updateHealthServiceCategoryRequest);
        return ResponseEntity.ok(ApiResponse.<HealthServiceCategoryResponse>builder()
                .code(200)
                .message("Healthy Service Category Updated")
                .result(result)
                .build());
    }

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<HealthServiceCategoryResponse>>> viewAllHealthServiceCategory() {
        var result = healthServiceCategoryService.getAllHealthServiceCategory();
        return ResponseEntity.ok(ApiResponse.<List<HealthServiceCategoryResponse>>builder()
                .code(200)
                .message("Healthy Service Category Viewed")
                .result(result)
                .build());
    }

    @PutMapping("delete/{id}")
    public ResponseEntity<ApiResponse<HealthServiceCategoryResponse>> deleteHealthServiceCategory(@PathVariable int id) {
        var result = healthServiceCategoryService.deleteHealthServiceCategory(id);
        return ResponseEntity.ok(ApiResponse.<HealthServiceCategoryResponse>builder()
                .code(200)
                .message("Healthy Service Category Deleted")
                .result(result)
                .build());
    }
}
