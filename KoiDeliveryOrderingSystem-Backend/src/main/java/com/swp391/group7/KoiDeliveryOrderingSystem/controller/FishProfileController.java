package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.dto.request.CreateFishProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.request.UpdateFishProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.response.FishProfileResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.FishProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fish-profile")
@RequiredArgsConstructor
public class FishProfileController {
    @Autowired
    private FishProfileService fishProfileService;

    @PostMapping("create")
    public ApiResponse<FishProfileResponse> createFishProfile(@RequestBody CreateFishProfileRequest createFishProfileRequest) {
        var result = fishProfileService.createFishProfile(createFishProfileRequest);
        return ApiResponse.<FishProfileResponse>builder()
                .code(200)
                .message("Fish profile created successfully")
                .result(result)
                .build();
    }
    @PutMapping("update/{fishProfileId}")
    public ApiResponse<FishProfileResponse> updateFishProfile(@PathVariable Integer fishProfileId,@RequestBody UpdateFishProfileRequest updateFishProfileRequest) {
        var result = fishProfileService.updateFishProfile(fishProfileId, updateFishProfileRequest);
        return ApiResponse.<FishProfileResponse>builder()
                .code(200)
                .message("Fish profile updated successfully")
                .result(result)
                .build();
    }

    @GetMapping("get-all")
    public ApiResponse<List<FishProfileResponse>> getAllFishProfile() {
        var result = fishProfileService.getAllFishProfiles();
        return ApiResponse.<List<FishProfileResponse>>builder()
                .code(200)
                .message("Fish profile list")
                .result(result)
                .build();
    }
}
