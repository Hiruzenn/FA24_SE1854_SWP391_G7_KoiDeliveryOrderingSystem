package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.CreateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.CreateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.UpdateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.CreateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishCategoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishCategoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.FishCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fish-category")
public class FishCategoryController {
    @Autowired
    private final FishCategoryService fishCategoryService;

    @Autowired
    public FishCategoryController(FishCategoryService fishCategoryService) {
        this.fishCategoryService = fishCategoryService;
    }

    @PostMapping("create")
    public ApiResponse<FishCategoryResponse> createFishCategory(@RequestBody CreateFishCategoryRequest createFishCategoryRequest) {
        var result = fishCategoryService.createFishCategory(createFishCategoryRequest);
        return ApiResponse.<FishCategoryResponse>builder()
                .code(200)
                .message("Fish Category Created")
                .result(result)
                .build();
    }

    @PutMapping("update/{id}")
    public ApiResponse<FishCategoryResponse> updateFishCategory(@PathVariable int id, @RequestBody UpdateFishCategoryRequest updateFishCategoryRequest) {
        var result = fishCategoryService.updateFishCategory(id, updateFishCategoryRequest);
        return ApiResponse.<FishCategoryResponse>builder()
                .code(200)
                .message("Fish Category Updated")
                .result(result)
                .build();
    }

    @GetMapping("view-all")
    public ApiResponse<List<FishCategoryResponse>> viewAllFishCategories() {
        var result = fishCategoryService.viewAllFishCategories();
        return ApiResponse.<List<FishCategoryResponse>>builder()
                .code(200)
                .message("Fish Category Viewed")
                .result(result)
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<FishCategoryResponse> deleteFishCategory(@PathVariable int id) {
       fishCategoryService.deleteFishCategory(id);
        return ApiResponse.<FishCategoryResponse>builder()
                .code(200)
                .message("Fish Category Deleted")
                .build();
    }
}