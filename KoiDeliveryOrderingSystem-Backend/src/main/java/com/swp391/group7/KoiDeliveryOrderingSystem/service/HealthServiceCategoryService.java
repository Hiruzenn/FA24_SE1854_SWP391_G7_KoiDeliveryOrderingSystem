package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceCategory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthyservicecategory.CreateHealthServiceCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthyservicecategory.UpdateHealthServiceCategoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthServiceCategoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HealthServiceCategoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthServiceCategoryService {
    @Autowired
    private HealthServiceCategoryRepository healthServiceCategoryRepository;

    @Autowired
    private final AccountUtils accountUtils;

    public HealthServiceCategoryResponse createHealthyServiceCategory(CreateHealthServiceCategoryRequest createHealthServiceCategoryRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        HealthServiceCategory healthServiceCategory = HealthServiceCategory.builder()
                .serviceName(createHealthServiceCategoryRequest.getServiceName())
                .serviceDescription(createHealthServiceCategoryRequest.getServiceDescription())
                .price(createHealthServiceCategoryRequest.getPrice())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE).build();
        healthServiceCategoryRepository.save(healthServiceCategory);
        return convertToHealthServiceCategoryResponse(healthServiceCategory);
    }

    public HealthServiceCategoryResponse updateHealthServiceCategory(Integer id, UpdateHealthServiceCategoryRequest updateHealthServiceCategoryRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        HealthServiceCategory healthServiceCategory = healthServiceCategoryRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTH_SERVICE_CATEGORY_NOT_FOUND));

        healthServiceCategory.setServiceName(updateHealthServiceCategoryRequest.getServiceName());
        healthServiceCategory.setServiceDescription(updateHealthServiceCategoryRequest.getServiceDescription());
        healthServiceCategory.setPrice(updateHealthServiceCategoryRequest.getPrice());
        healthServiceCategory.setUpdateAt(LocalDateTime.now());
        healthServiceCategory.setUpdateBy(users.getId());
        healthServiceCategoryRepository.save(healthServiceCategory);
        return convertToHealthServiceCategoryResponse(healthServiceCategory);
    }

    public List<HealthServiceCategoryResponse> getAllHealthServiceCategory() {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        List<HealthServiceCategory> healthServiceCategories = healthServiceCategoryRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        List<HealthServiceCategoryResponse> healthServiceCategoryResponses = new ArrayList<>();
        for (HealthServiceCategory healthServiceCategory : healthServiceCategories) {
            healthServiceCategoryResponses.add(convertToHealthServiceCategoryResponse(healthServiceCategory));
        }
        return healthServiceCategoryResponses;

    }

    public HealthServiceCategoryResponse deleteHealthServiceCategory(Integer id) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        HealthServiceCategory healthServiceCategory = healthServiceCategoryRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTH_SERVICE_CATEGORY_NOT_FOUND));
        healthServiceCategory.setUpdateAt(LocalDateTime.now());
        healthServiceCategory.setUpdateBy(users.getId());
        healthServiceCategory.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        healthServiceCategoryRepository.save(healthServiceCategory);
        return convertToHealthServiceCategoryResponse(healthServiceCategory);
    }

    public HealthServiceCategoryResponse convertToHealthServiceCategoryResponse(HealthServiceCategory healthServiceCategory) {
        return HealthServiceCategoryResponse.builder()
                .id(healthServiceCategory.getId())
                .serviceName(healthServiceCategory.getServiceName())
                .serviceDescription(healthServiceCategory.getServiceDescription())
                .price(healthServiceCategory.getPrice())
                .createAt(healthServiceCategory.getCreateAt())
                .createBy(healthServiceCategory.getCreateBy())
                .updateAt(healthServiceCategory.getUpdateAt())
                .updateBy(healthServiceCategory.getUpdateBy())
                .status(healthServiceCategory.getStatus())
                .build();
    }
}
