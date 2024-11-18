package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.CheckingKoiHealth;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth.CreateCheckingKoiHealthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth.UpdateCheckingKoiHealthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CheckingKoiHealthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CheckingKoiHealthRepository;
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
public class CheckingKoiHealthService {
    @Autowired
    private CheckingKoiHealthRepository checkingKoiHealthRepository;

    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private FishProfileRepository fishProfileRepository;

    public CheckingKoiHealthResponse createCheckingKoiHealth(Integer id, CreateCheckingKoiHealthRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        FishProfile fishProfile = fishProfileRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_PROFILE_NOT_FOUND));
        CheckingKoiHealth checkingKoiHealth = CheckingKoiHealth.builder()
                .fishProfile(fishProfile)
                .healthStatus(request.getHealthStatus())
                .healthStatusDescription(request.getHealthStatusDescription())
                .weight(request.getWeight())
                .sex(request.getSex())
                .color(request.getColor())
                .species(request.getSpecies())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        checkingKoiHealthRepository.save(checkingKoiHealth);
        return convertToCheckingKoiHealthResponse(checkingKoiHealth);
    }

    public CheckingKoiHealthResponse updateCheckingKoiHealth(Integer id, UpdateCheckingKoiHealthRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        CheckingKoiHealth checkingKoiHealth = checkingKoiHealthRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.CHECKING_KOI_HEALTH_NOT_FOUND));
        checkingKoiHealth.setHealthStatus(request.getHealthStatus());
        checkingKoiHealth.setHealthStatusDescription(request.getHealthStatusDescription());
        checkingKoiHealth.setWeight(request.getWeight());
        checkingKoiHealth.setSex(request.getSex());
        checkingKoiHealth.setColor(request.getColor());
        checkingKoiHealth.setSpecies(request.getSpecies());
        checkingKoiHealth.setUpdateAt(LocalDateTime.now());
        checkingKoiHealth.setUpdateBy(users.getId());
        checkingKoiHealthRepository.save(checkingKoiHealth);
        return convertToCheckingKoiHealthResponse(checkingKoiHealth);
    }

    public List<CheckingKoiHealthResponse> getAllCheckingKoiHealth() {
        List<CheckingKoiHealth> checkingKoiHealthList = checkingKoiHealthRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        return convertToListCheckingKoiHealthResponse(checkingKoiHealthList);
    }


    public List<CheckingKoiHealthResponse> viewCheckingKoiHealthByFishProfile(Integer fishProfileId) {
        FishProfile fishProfile = fishProfileRepository.findByIdAndStatus(fishProfileId, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_PROFILE_NOT_FOUND));
        List<CheckingKoiHealth> checkingKoiHealthList = checkingKoiHealthRepository.findByFishProfileAndStatus(fishProfile, SystemStatusEnum.AVAILABLE);
        return convertToListCheckingKoiHealthResponse(checkingKoiHealthList);
    }

    public Boolean existedCheckingKoiHealth(Integer fishProfileId){
        FishProfile fishProfile = fishProfileRepository.findByIdAndStatus(fishProfileId, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_PROFILE_NOT_FOUND));
        return checkingKoiHealthRepository.existsByFishProfileAndStatus(fishProfile, SystemStatusEnum.AVAILABLE);
    }

    public CheckingKoiHealthResponse deleteCheckingKoiHealth(Integer id) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        CheckingKoiHealth checkingKoiHealth = checkingKoiHealthRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.CHECKING_KOI_HEALTH_NOT_FOUND));
        checkingKoiHealth.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        checkingKoiHealth.setUpdateAt(LocalDateTime.now());
        checkingKoiHealth.setUpdateBy(users.getId());
        checkingKoiHealthRepository.save(checkingKoiHealth);
        return convertToCheckingKoiHealthResponse(checkingKoiHealth);
    }

    public List<CheckingKoiHealthResponse> convertToListCheckingKoiHealthResponse(List<CheckingKoiHealth> checkingKoiHealths) {
        List<CheckingKoiHealthResponse> checkingKoiHealthResponseList = new ArrayList<>();
        for (CheckingKoiHealth checkingKoiHealth : checkingKoiHealths) {
            checkingKoiHealthResponseList.add(convertToCheckingKoiHealthResponse(checkingKoiHealth));
        }
        return checkingKoiHealthResponseList;
    }

    public CheckingKoiHealthResponse convertToCheckingKoiHealthResponse(CheckingKoiHealth checkingKoi) {
        return CheckingKoiHealthResponse.builder()
                .id(checkingKoi.getId())
                .fishProfileId(checkingKoi.getFishProfile().getId())
                .healthStatus(checkingKoi.getHealthStatus())
                .healthStatusDescription(checkingKoi.getHealthStatusDescription())
                .weight(checkingKoi.getWeight())
                .sex(checkingKoi.getSex())
                .color(checkingKoi.getColor())
                .species(checkingKoi.getSpecies())
                .createAt(checkingKoi.getCreateAt())
                .createBy(checkingKoi.getCreateBy())
                .updateAt(checkingKoi.getUpdateAt())
                .updateBy(checkingKoi.getUpdateBy())
                .status(checkingKoi.getStatus())
                .build();
    }
}
