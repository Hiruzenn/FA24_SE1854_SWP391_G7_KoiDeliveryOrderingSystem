package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.CreateFishProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.UpdateFishProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishProfileResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Customers;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishCategoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishProfileRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderDetailRepository;
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
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private FishCategoryRepository fishCategoryRepository;

    public FishProfileResponse createFishProfile(CreateFishProfileRequest createFishProfileRequest) {
        Customers customers = accountUtils.getCurrentCustomer();
        if (customers == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        FishProfile fishProfile = FishProfile.builder()
                .name(createFishProfileRequest.getName())
                .description(createFishProfileRequest.getDescription())
                .type(fishCategoryRepository.findByFishCategoryName(createFishProfileRequest.getType()))
                .size(createFishProfileRequest.getSize())
                .origin(createFishProfileRequest.getOrigin())
                .image(createFishProfileRequest.getImage())
                .createAt(LocalDateTime.now())
                .createBy(customers.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(customers.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        fishProfileRepository.save(fishProfile);
        return convertToFishProfileResponse(fishProfile);
    }

    public FishProfileResponse updateFishProfile(Integer fishProfileId, UpdateFishProfileRequest updateFishProfileRequest) {
        Customers customers = accountUtils.getCurrentCustomer();
        FishProfile fishProfile = fishProfileRepository.findById(fishProfileId).orElseThrow(()-> new AppException(ErrorCode.FISH_PROFILE_NOT_FOUND));
        if (customers == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        fishProfile.setName(updateFishProfileRequest.getName());
        fishProfile.setDescription(updateFishProfileRequest.getDescription());
        fishProfile.setType(fishCategoryRepository.findByFishCategoryName(updateFishProfileRequest.getType()));
        fishProfile.setSize(updateFishProfileRequest.getSize());
        fishProfile.setOrigin(updateFishProfileRequest.getOrigin());
        fishProfile.setImage(updateFishProfileRequest.getImage());
        fishProfile.setUpdateAt(LocalDateTime.now());
        fishProfile.setUpdateBy(customers.getId());
        fishProfileRepository.save(fishProfile);
        return convertToFishProfileResponse(fishProfile);
    }

    public List<FishProfileResponse> getAllFishProfiles() {
        Customers customers = accountUtils.getCurrentCustomer();
        if (customers == null){
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        List<FishProfile> fishProfiles = fishProfileRepository.findAll();
        List<FishProfileResponse> fishProfileResponses = new ArrayList<>();
        for(FishProfile fishProfile : fishProfiles){
            fishProfileResponses.add(convertToFishProfileResponse(fishProfile));
        }
        return fishProfileResponses;
    }
    public FishProfileResponse convertToFishProfileResponse(FishProfile fishProfile) {
        return FishProfileResponse.builder()
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
