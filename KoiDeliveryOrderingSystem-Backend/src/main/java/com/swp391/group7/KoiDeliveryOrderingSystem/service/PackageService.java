package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HandoverStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HealthStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.PackageStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.packages.CreatePackageRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.packages.UpdatePackageRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PackageResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.*;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private HandoverDocumentRepository handoverDocumentRepository;
    @Autowired
    private FishProfileRepository fishProfileRepository;
    @Autowired
    private CheckingKoiHealthRepository checkingKoiHealthRepository;
    public static final String RANDOM_STRING = "0123456789";
    @Autowired
    private CheckingKoiHealthService checkingKoiHealthService;

    public PackageResponse createPackage(Integer orderId, CreatePackageRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        boolean existedCheckingKoiHealth = fishProfileRepository.findByOrders(orders)
                .stream().allMatch(checkingKoiHealthRepository::existsByFishProfile);
        if (!existedCheckingKoiHealth) {
            throw new AppException(ErrorCode.NOT_ENOUGH_CHECKING_KOI_HEALTH);
        }
        boolean checkingKoiHealthy = fishProfileRepository.findByOrders(orders).stream().allMatch(fishProfile -> {
            Optional<CheckingKoiHealth> latestCheck = checkingKoiHealthRepository.findTopByFishProfileOrderByCreateAtDesc(fishProfile);
            return latestCheck.isPresent() && latestCheck.get().getHealthStatus() == HealthStatusEnum.HEALTHY;
        });
        if (!checkingKoiHealthy) {
            throw new AppException(ErrorCode.PACKAGE_FISH_HEALTHY);
        }
        if (!handoverDocumentRepository.existsByOrders(orders)) {
            throw new AppException(ErrorCode.REQUIRED_HANDOVER_DOCUMENT);
        }
        if (packageRepository.existsByOrders(orders)) {
            throw new AppException(ErrorCode.PACKAGE_EXISTED);
        }
        Package packages = Package.builder()
                .orders(orders)
                .packageNo(generatePackageNo())
                .packageDescription(request.getDescription())
                .packageStatus(PackageStatusEnum.UNPACKED)
                .image(request.getImage())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .build();
        packageRepository.save(packages);
        return convertToPackageResponse(packages);

    }

    public PackageResponse updatePackage(Integer orderId, UpdatePackageRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Package packages = packageRepository.findByOrders(orders).orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        HandoverDocument handoverDocument = handoverDocumentRepository.findByOrders(orders)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        if (request.getPackageStatus().equals(PackageStatusEnum.PACKED)) {
            handoverDocument.setHandoverStatus(HandoverStatusEnum.IN_PROGRESS);
            orders.setEstimateDeliveryDate(LocalDateTime.now());
            orders.setStatus(OrderStatusEnum.IN_PROGRESS);
        }
        packages.setPackageDescription(request.getDescription());
        packages.setPackageStatus(request.getPackageStatus());
        packages.setImage(request.getImage());
        packages.setUpdateAt(LocalDateTime.now());
        packages.setUpdateBy(users.getId());
        packageRepository.save(packages);
        handoverDocumentRepository.save(handoverDocument);
        orderRepository.save(orders);
        return convertToPackageResponse(packages);
    }

    public List<PackageResponse> getAllPackages() {
        List<Package> packages = packageRepository.findAll();
        return convertToListPackageResponse(packages);
    }

    public PackageResponse getPackageByPackageNo(String packageNo) {
        Package packages = packageRepository.findByPackageNo(packageNo);
        return convertToPackageResponse(packages);
    }

    public PackageResponse viewPackageByOrder(Integer orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Package packages = packageRepository.findByOrders(orders).orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        return convertToPackageResponse(packages);
    }

    public void deletePackage(Integer id) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Package packages = packageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        if (packages.getPackageStatus().equals(PackageStatusEnum.PACKED)) {
            throw new AppException(ErrorCode.NOT_DELETE_PACKED);
        }
        packageRepository.delete(packages);
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
                .packageStatus(packages.getPackageStatus())
                .image(packages.getImage())
                .createAt(packages.getCreateAt())
                .createBy(packages.getCreateBy())
                .updateAt(packages.getUpdateAt())
                .updateBy(packages.getUpdateBy())
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
