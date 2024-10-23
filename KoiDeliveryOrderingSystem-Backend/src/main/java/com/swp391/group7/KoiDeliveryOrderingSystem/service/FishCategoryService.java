package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishCategory;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.CreateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.UpdateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishCategoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FishCategoryService {

    @Autowired
    private FishCategoryRepository fishCategoryRepository;
    public FishCategoryResponse createFishCategory(CreateFishCategoryRequest createFishCategoryRequest) {
        FishCategory fishCategory = FishCategory.builder()
                .fishCategoryName(createFishCategoryRequest.getFish_category_name())
                .fishCategoryDescription(createFishCategoryRequest.getFish_category_description())
                .build();
        fishCategoryRepository.save(fishCategory);
        return convertToFishCategoryResponse(fishCategory);
    }

    public FishCategoryResponse updateFishCategory(Integer id, UpdateFishCategoryRequest updateFishCategoryRequest) {
        FishCategory fishCategory = fishCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_CATEGORY_NOT_FOUND));

        fishCategory.setFishCategoryName(updateFishCategoryRequest.getFish_category_name());
        fishCategory.setFishCategoryDescription(updateFishCategoryRequest.getFish_category_description());
        fishCategoryRepository.save(fishCategory);

        return convertToFishCategoryResponse(fishCategory);
    }

    public List<FishCategoryResponse> viewAllFishCategories() {
        List<FishCategory> fishCategories = fishCategoryRepository.findAll();
        List<FishCategoryResponse> fishCategoryResponses = new ArrayList<>();

        for (FishCategory fishCategory : fishCategories) {
            FishCategoryResponse response = convertToFishCategoryResponse(fishCategory);
            fishCategoryResponses.add(response);
        }

        return fishCategoryResponses;
    }

    public FishCategoryResponse getFishCategoryById(Integer id) {
        FishCategory fishCategory = fishCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_CATEGORY_NOT_FOUND));

        return convertToFishCategoryResponse(fishCategory);
    }

    public FishCategoryResponse deleteFishCategory(Integer id) {
        FishCategory fishCategory = fishCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_CATEGORY_NOT_FOUND));

        FishCategoryResponse response = convertToFishCategoryResponse(fishCategory);
        fishCategoryRepository.deleteById(id);

        return response;
    }

    private FishCategoryResponse convertToFishCategoryResponse(FishCategory fishCategory) {
        return FishCategoryResponse.builder()
                .id(fishCategory.getId())
                .fish_category_name(fishCategory.getFishCategoryName())
                .fish_category_description(fishCategory.getFishCategoryDescription())
                .build();
    }
}