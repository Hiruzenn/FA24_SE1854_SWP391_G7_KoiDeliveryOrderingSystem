package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthyservicecategory.CreateHealthServiceCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthyservicecategory.UpdateHealthServiceCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthServiceCategoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.HealthServiceCategoryService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("heal-service-category")
@RequiredArgsConstructor
public class HealthServiceCategoryController {
    @Autowired
    private final HealthServiceCategoryService healthServiceCategoryService;

    @PostMapping("create")
    public ApiResponse<HealthServiceCategoryResponse> createHealthyServiceCategory(@RequestBody CreateHealthServiceCategoryRequest createHealthServiceCategoryRequest) {
        var result = healthServiceCategoryService.createHealthyServiceCategory(createHealthServiceCategoryRequest);
        return ApiResponse.<HealthServiceCategoryResponse>builder()
               .code(200)
                .message("Healthy Service Category Created")
                .result(result)
                .build();
    }

    @PutMapping("update/{id}")
    public ApiResponse<HealthServiceCategoryResponse> updateHealthServiceCategory(@PathVariable int id, @RequestBody UpdateHealthServiceCategoryRequest updateHealthServiceCategoryRequest) {
        var result = healthServiceCategoryService.updateHealthServiceCategory(id, updateHealthServiceCategoryRequest);
        return ApiResponse.<HealthServiceCategoryResponse>builder()
                .code(200)
                .message("Healthy Service Category Updated")
                .result(result)
                .build();
    }

    @GetMapping("view-all")
    public ApiResponse<List<HealthServiceCategoryResponse>> viewAllHealthServiceCategory() {
        var result = healthServiceCategoryService.getAllHealthServiceCategory();
        return ApiResponse.<List<HealthServiceCategoryResponse>>builder()
                .code(200)
                .message("Healthy Service Category Viewed")
                .result(result)
                .build();
    }

    @PutMapping("delete/{id}")
    public ApiResponse<HealthServiceCategoryResponse> deleteHealthServiceCategory(@PathVariable int id) {
        healthServiceCategoryService.deleteHealthServiceCategory(id);
        return ApiResponse.<HealthServiceCategoryResponse>builder()
                .code(200)
                .message("Healthy Service Category Deleted")
                .build();
    }
}
