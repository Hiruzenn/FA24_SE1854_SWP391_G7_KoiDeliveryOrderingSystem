package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishCategory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.CreateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishcategory.UpdateFishCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishCategoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishCategoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FishCategoryService {
    @Autowired
    private FishCategoryRepository fishCategoryRepository;
    @Autowired
    private AccountUtils accountUtils;

    public FishCategoryResponse createFishCategory(CreateFishCategoryRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        FishCategory fishCategory = FishCategory.builder()
                .name(request.getFishCategoryName())
                .description(request.getFishCategoryDescription())
                .price(request.getPrice())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        fishCategoryRepository.save(fishCategory);
        return convertToFishCategoryResponse(fishCategory);
    }

    public FishCategoryResponse updateFishCategory(Integer id, UpdateFishCategoryRequest updateFishCategoryRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        FishCategory fishCategory = fishCategoryRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_CATEGORY_NOT_FOUND));
        fishCategory.setName(updateFishCategoryRequest.getFishCategoryName());
        fishCategory.setDescription(updateFishCategoryRequest.getFishCategoryDescription());
        fishCategory.setPrice(updateFishCategoryRequest.getPrice());
        fishCategory.setUpdateAt(LocalDateTime.now());
        fishCategory.setUpdateBy(users.getId());
        fishCategoryRepository.save(fishCategory);

        return convertToFishCategoryResponse(fishCategory);
    }

    public List<FishCategoryResponse> viewAllFishCategories() {
        List<FishCategory> fishCategories = fishCategoryRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        List<FishCategoryResponse> fishCategoryResponses = new ArrayList<>();
        for (FishCategory fishCategory : fishCategories) {
            fishCategoryResponses.add(convertToFishCategoryResponse(fishCategory));
        }
        return fishCategoryResponses;
    }

    public FishCategoryResponse deleteFishCategory(Integer id) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        FishCategory fishCategory = fishCategoryRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_CATEGORY_NOT_FOUND));
        fishCategory.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        fishCategory.setUpdateAt(LocalDateTime.now());
        fishCategory.setUpdateBy(users.getId());
        fishCategoryRepository.save(fishCategory);
        return convertToFishCategoryResponse(fishCategory);
    }

    private FishCategoryResponse convertToFishCategoryResponse(FishCategory fishCategory) {
        return FishCategoryResponse.builder()
                .id(fishCategory.getId())
                .fishCategoryName(fishCategory.getName())
                .fishCategoryDescription(fishCategory.getDescription())
                .createAt(fishCategory.getCreateAt())
                .createBy(fishCategory.getCreateBy())
                .updateAt(fishCategory.getUpdateAt())
                .updateBy(fishCategory.getUpdateBy())
                .status(fishCategory.getStatus())
                .build();
    }
}