package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishCategory;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishprofile.CreateFishProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.fishprofile.UpdateFishProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishCategoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishProfileResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishCategoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishProfileRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FishProfileService {
    @Autowired
    private FishProfileRepository fishProfileRepository;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private FishCategoryRepository fishCategoryRepository;

    public FishProfileResponse createFishProfile(CreateFishProfileRequest createFishProfileRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        FishCategory fishCategory = fishCategoryRepository.findByFishCategoryName(createFishProfileRequest.getType());
        if (fishCategory == null) {
            throw new AppException(ErrorCode.FISH_CATEGORY_NOT_FOUND);
        }
        FishProfile fishProfile = FishProfile.builder()
                .name(createFishProfileRequest.getName())
                .description(createFishProfileRequest.getDescription())
                .type(fishCategory)
                .size(createFishProfileRequest.getSize())
                .origin(createFishProfileRequest.getOrigin())
                .image(createFishProfileRequest.getImage())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        fishProfileRepository.save(fishProfile);
        return convertToFishProfileResponse(fishProfile);
    }

    public FishProfileResponse updateFishProfile(Integer fishProfileId, UpdateFishProfileRequest updateFishProfileRequest) {
        Users users = accountUtils.getCurrentUser();
        FishProfile fishProfile = fishProfileRepository.findByIdAndStatus(fishProfileId, SystemStatusEnum.AVAILABLE).orElseThrow(() -> new AppException(ErrorCode.FISH_PROFILE_NOT_FOUND));
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        FishCategory fishCategory = fishCategoryRepository.findByFishCategoryName(updateFishProfileRequest.getType());
        if (fishCategory == null) {
            throw new AppException(ErrorCode.FISH_CATEGORY_NOT_FOUND);
        }
        fishProfile.setName(updateFishProfileRequest.getName());
        fishProfile.setDescription(updateFishProfileRequest.getDescription());
        fishProfile.setType(fishCategory);
        fishProfile.setSize(updateFishProfileRequest.getSize());
        fishProfile.setOrigin(updateFishProfileRequest.getOrigin());
        fishProfile.setImage(updateFishProfileRequest.getImage());
        fishProfile.setUpdateAt(LocalDateTime.now());
        fishProfile.setUpdateBy(users.getId());
        fishProfileRepository.save(fishProfile);
        return convertToFishProfileResponse(fishProfile);
    }

    public List<FishProfileResponse> getAllFishProfiles() {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        List<FishProfile> fishProfiles = fishProfileRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        List<FishProfileResponse> fishProfileResponses = new ArrayList<>();
        for (FishProfile fishProfile : fishProfiles) {
            fishProfileResponses.add(convertToFishProfileResponse(fishProfile));
        }
        return fishProfileResponses;
    }

    public FishProfileResponse viewOne(Integer fishProfileId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        FishProfile fishProfile = fishProfileRepository.findByIdAndStatus(fishProfileId, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_PROFILE_NOT_FOUND));
        return convertToFishProfileResponse(fishProfile);
    }

    public FishProfileResponse deleteFishProfile(Integer fishProfileId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        FishProfile fishProfile = fishProfileRepository.findByIdAndStatus(fishProfileId, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_PROFILE_NOT_FOUND));
        fishProfile.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        fishProfile.setUpdateAt(LocalDateTime.now());
        fishProfile.setUpdateBy(users.getId());
        fishProfileRepository.save(fishProfile);
        return convertToFishProfileResponse(fishProfile);
    }

    public FishProfileResponse convertToFishProfileResponse(FishProfile fishProfile) {
        return FishProfileResponse.builder()
                .id(fishProfile.getId())
                .name(fishProfile.getName())
                .description(fishProfile.getDescription())
                .type(fishProfile.getType().getFishCategoryName())
                .size(fishProfile.getSize())
                .origin(fishProfile.getOrigin())
                .image(fishProfile.getImage())
                .createAt(fishProfile.getCreateAt())
                .createBy(fishProfile.getCreateBy())
                .updateAt(fishProfile.getUpdateAt())
                .updateBy(fishProfile.getUpdateBy())
                .status(fishProfile.getStatus())
                .build();
    }
}
