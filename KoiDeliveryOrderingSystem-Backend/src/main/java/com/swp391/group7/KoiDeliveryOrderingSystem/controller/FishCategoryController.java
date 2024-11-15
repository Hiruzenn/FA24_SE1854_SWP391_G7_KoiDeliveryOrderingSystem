package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.CreateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.CreateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.UpdateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.CreateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishCategoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishCategoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.FishCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fish-category")
@Tag(name = "Fish Category")
public class FishCategoryController {
    @Autowired
    private final FishCategoryService fishCategoryService;

    @Autowired
    public FishCategoryController(FishCategoryService fishCategoryService) {
        this.fishCategoryService = fishCategoryService;
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<FishCategoryResponse>> createFishCategory(@Valid() @RequestBody CreateFishCategoryRequest createFishCategoryRequest) {
        var result = fishCategoryService.createFishCategory(createFishCategoryRequest);
        return ResponseEntity.ok(ApiResponse.<FishCategoryResponse>builder()
                .code(200)
                .message("Fish Category Created")
                .result(result)
                .build());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<FishCategoryResponse>> updateFishCategory(@Valid() @PathVariable int id, @RequestBody UpdateFishCategoryRequest updateFishCategoryRequest) {
        var result = fishCategoryService.updateFishCategory(id, updateFishCategoryRequest);
        return ResponseEntity.ok(ApiResponse.<FishCategoryResponse>builder()
                .code(200)
                .message("Fish Category Updated")
                .result(result)
                .build());
    }

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<FishCategoryResponse>>> viewAllFishCategories() {
        var result = fishCategoryService.viewAllFishCategories();
        return ResponseEntity.ok(ApiResponse.<List<FishCategoryResponse>>builder()
                .code(200)
                .message("Fish Category Viewed")
                .result(result)
                .build());
    }

    @PutMapping("delete/{id}")
    public ResponseEntity<ApiResponse<FishCategoryResponse>> deleteFishCategory(@PathVariable int id) {
        var result = fishCategoryService.deleteFishCategory(id);
        return ResponseEntity.ok(ApiResponse.<FishCategoryResponse>builder()
                .code(200)
                .message("Fish Category Deleted")
                .result(result)
                .build());
    }
}