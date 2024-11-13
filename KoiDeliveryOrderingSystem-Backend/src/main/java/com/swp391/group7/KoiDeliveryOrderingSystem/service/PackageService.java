package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.packages.CreatePackageRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.packages.UpdatePackageRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PackageResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.PackageRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private AccountUtils accountUtils;

    public static final String RANDOM_STRING = "0123456789";
    @Autowired
    private OrderRepository orderRepository;

    public PackageResponse createPackage(Integer orderId,CreatePackageRequest createPackageRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));;
        Package packages = Package.builder()
                .orders(orders)
                .packageNo(generatePackageNo())
                .packageDescription(createPackageRequest.getPackageDescription())
                .packageDate(createPackageRequest.getPackageDate())
                .packageStatus(createPackageRequest.getPackageStatus())
                .packageBy(createPackageRequest.getPackageBy())
                .image(createPackageRequest.getImage())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        packageRepository.save(packages);
        return convertToPackageResponse(packages);

    }

    public PackageResponse updatePackage(Integer packageId, UpdatePackageRequest updatePackageRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Package packages = packageRepository.findByIdAndStatus(packageId, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        packages.setPackageStatus(updatePackageRequest.getPackageStatus());
        packages.setImage(updatePackageRequest.getImage());
        packages.setUpdateAt(LocalDateTime.now());
        packages.setUpdateBy(users.getId());
        packageRepository.save(packages);
        return convertToPackageResponse(packages);
    }

    public List<PackageResponse> getAllPackages() {
        List<Package> packages = packageRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        return convertToListPackageResponse(packages);
    }

    public PackageResponse getPackageByPackageNo(String packageNo){
        Package packages = packageRepository.findByPackageNoAndStatus(packageNo, SystemStatusEnum.AVAILABLE);
        return convertToPackageResponse(packages);
    }

    public PackageResponse deletePackage(Integer id){
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        Package packages = packageRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        packages.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        packages.setUpdateAt(LocalDateTime.now());
        packages.setUpdateBy(users.getId());
        packageRepository.save(packages);
        return convertToPackageResponse(packages);
    }

    public List<PackageResponse> convertToListPackageResponse(List<Package> packageList) {
        List<PackageResponse> packageResponseList = new ArrayList<>();
        for (Package packages : packageList) {
            packageResponseList.add(convertToPackageResponse(packages));
        }
        return packageResponseList;
    }

    public PackageResponse convertToPackageResponse(Package packages) {
        return PackageResponse.builder()
                .id(packages.getId())
                .packageNo(packages.getPackageNo())
                .packageDescription(packages.getPackageDescription())
                .packageDate(packages.getPackageDate())
                .packageStatus(packages.getPackageStatus())
                .packagedBy(packages.getPackageBy())
                .image(packages.getImage())
                .createAt(packages.getCreateAt())
                .createBy(packages.getCreateBy())
                .updateAt(packages.getUpdateAt())
                .updateBy(packages.getUpdateBy())
                .status(packages.getStatus())
                .build();
    }

    private String generatePackageNo() {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder;
        String packageNo = "";
        do {
            stringBuilder = new StringBuilder("PAC");
            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(5);
                stringBuilder.append(RANDOM_STRING.charAt(randomIndex));
            }
            packageNo = stringBuilder.toString();
        } while (packageRepository.existsByPackageNo(packageNo));
        return packageNo;
    }
}
