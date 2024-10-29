package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.CheckingKoiHealth;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.OrderDetail;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth.CreateCheckingKoiHealthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.checkingkoihealth.UpdateCheckingKoiHealthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CheckingKoiHealthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CheckingKoiHealthRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderDetailRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.PackageRepository;
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
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private AccountUtils accountUtils;

    public CheckingKoiHealthResponse createCheckingKoiHealth(Integer orderDetailId, Integer packageId, CreateCheckingKoiHealthRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));
        Package packages = packageRepository.findById(packageId)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        CheckingKoiHealth checkingKoiHealth = CheckingKoiHealth.builder()
                .orderDetail(orderDetail)
                .packages(packages)
                .healthStatus(request.getHealthStatus())
                .healthStatusDescription(request.getHealthStatusDescription())
                .weight(request.getWeight())
                .color(request.getColor())
                .type(request.getType())
                .age(request.getAge())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        checkingKoiHealthRepository.save(checkingKoiHealth);
        return convertToCheckingKoiHealthResponse(checkingKoiHealth);
    }

    public CheckingKoiHealthResponse updateCheckingKoiHealth(Integer id, UpdateCheckingKoiHealthRequest updateCheckingKoiHealthRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        CheckingKoiHealth checkingKoiHealth = checkingKoiHealthRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.CHECKING_KOI_HEALTH_NOT_FOUND));
        checkingKoiHealth.setHealthStatus(updateCheckingKoiHealthRequest.getHealthStatus());
        checkingKoiHealth.setHealthStatusDescription(updateCheckingKoiHealthRequest.getHealthStatusDescription());
        checkingKoiHealth.setWeight(updateCheckingKoiHealthRequest.getWeight());
        checkingKoiHealth.setColor(updateCheckingKoiHealthRequest.getColor());
        checkingKoiHealth.setType(updateCheckingKoiHealthRequest.getType());
        checkingKoiHealth.setAge(updateCheckingKoiHealthRequest.getAge());
        checkingKoiHealth.setUpdateAt(LocalDateTime.now());
        checkingKoiHealth.setUpdateBy(users.getId());
        checkingKoiHealthRepository.save(checkingKoiHealth);
        return convertToCheckingKoiHealthResponse(checkingKoiHealth);
    }

    public List<CheckingKoiHealthResponse> getAllCheckingKoiHealth() {
        List<CheckingKoiHealth> checkingKoiHealthList = checkingKoiHealthRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        return convertToListCheckingKoiHealthResponse(checkingKoiHealthList);
    }

    public List<CheckingKoiHealthResponse> viewCheckingKoiHealthByPackage(Integer packageId) {
        Package packages = packageRepository.findByIdAndStatus(packageId, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        List<CheckingKoiHealth> checkingKoiHealthList = checkingKoiHealthRepository.findByPackagesAndStatus(packages, SystemStatusEnum.AVAILABLE);
        return convertToListCheckingKoiHealthResponse(checkingKoiHealthList);
    }

    public List<CheckingKoiHealthResponse> viewCheckingKoiHealthByOrderDetail(Integer orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findByIdAndStatus(orderDetailId, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));
        List<CheckingKoiHealth> checkingKoiHealthList = checkingKoiHealthRepository.findByOrderDetailAndStatus(orderDetail, SystemStatusEnum.AVAILABLE);
        return convertToListCheckingKoiHealthResponse(checkingKoiHealthList);
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
                .orderDetailId(checkingKoi.getOrderDetail().getId())
                .packageId(checkingKoi.getPackages().getId())
                .weight(checkingKoi.getWeight())
                .color(checkingKoi.getColor())
                .type(checkingKoi.getType())
                .age(checkingKoi.getAge())
                .createAt(checkingKoi.getCreateAt())
                .createBy(checkingKoi.getCreateBy())
                .updateAt(checkingKoi.getUpdateAt())
                .updateBy(checkingKoi.getUpdateBy())
                .status(checkingKoi.getStatus())
                .build();
    }
}
