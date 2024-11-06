package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishprofile.CreateFishProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishprofile.UpdateFishProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishCategoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishProfileResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.FishProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fish-profile")
@RequiredArgsConstructor
public class FishProfileController {
    @Autowired
    private FishProfileService fishProfileService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<FishProfileResponse>> createFishProfile(@Valid @RequestBody CreateFishProfileRequest createFishProfileRequest) {
        var result = fishProfileService.createFishProfile(createFishProfileRequest);
        return ResponseEntity.ok(ApiResponse.<FishProfileResponse>builder()
                .code(200)
                .message("Fish profile created successfully")
                .result(result)
                .build());
    }

    @PutMapping("update/{fishProfileId}")
    public ResponseEntity<ApiResponse<FishProfileResponse>> updateFishProfile(@Valid @PathVariable Integer fishProfileId, @RequestBody UpdateFishProfileRequest updateFishProfileRequest) {
        var result = fishProfileService.updateFishProfile(fishProfileId, updateFishProfileRequest);
        return ResponseEntity.ok(ApiResponse.<FishProfileResponse>builder()
                .code(200)
                .message("Fish profile updated successfully")
                .result(result)
                .build());
    }

    @GetMapping("get-all")
    public ResponseEntity<ApiResponse<List<FishProfileResponse>>> getAllFishProfile() {
        var result = fishProfileService.getAllFishProfiles();
        return ResponseEntity.ok(ApiResponse.<List<FishProfileResponse>>builder()
                .code(200)
                .message("Fish profile list")
                .result(result)
                .build());
    }

    @GetMapping("view-one/{id}")
    public ResponseEntity<ApiResponse<FishProfileResponse>> viewFishProfile(@PathVariable Integer id) {
        var result = fishProfileService.viewOne(id);
        return ResponseEntity.ok(ApiResponse.<FishProfileResponse>builder()
                .code(200)
                .message("Fish Profile By Id")
                .result(result)
                .build());
    }

    @GetMapping("delete/{fishProfileId}")
    public ResponseEntity<ApiResponse<FishProfileResponse>> deleteFishProfile(@PathVariable Integer fishProfileId) {
        var result = fishProfileService.deleteFishProfile(fishProfileId);
        return ResponseEntity.ok(ApiResponse.<FishProfileResponse>builder()
                .code(200)
                .message("Fish profile list")
                .result(result)
                .build());
    }
}
