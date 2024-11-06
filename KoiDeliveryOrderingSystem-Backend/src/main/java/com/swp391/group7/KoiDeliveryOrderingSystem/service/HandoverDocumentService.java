package com.swp391.group7.KoiDeliveryOrderingSystem.service;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HandoverDocument;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.CreateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.UpdateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HandoverDocumentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HandoverDocumentService {

    @Autowired
    private HandoverDocumentRepository handoverDocumentRepository;

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountUtils accountUtils;

    @Autowired
    private UserRepository userRepository;

    public static final String RANDOM_STRING = "0123456789";

    public HandoverDocumentResponse create(CreateHandoverDocumentRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Users customer = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Package packages = packageRepository.findByIdAndStatus(request.getPackageId(), SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        Orders orders = orderRepository.findByIdAndStatus(request.getOrderId(), OrderStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        HandoverDocument handoverDocument = HandoverDocument.builder()
                .handoverNo(generateHandoverNo())
                .users(customer)
                .orders(orders)
                .packages(packages)
                .handoverDescription(request.getHandoverDescription())
                .vehicle(request.getVehicle())
                .destination(request.getDestination())
                .departure(request.getDeparture())
                .totalPrice(request.getTotalPrice())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        handoverDocumentRepository.save(handoverDocument);
        return convertToHandoverDocumentResponse(handoverDocument);
    }

    public HandoverDocumentResponse update(Integer id, UpdateHandoverDocumentRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        HandoverDocument handoverDocument = handoverDocumentRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        handoverDocument.setHandoverDescription(request.getHandoverDescription());
        handoverDocument.setVehicle(request.getVehicle());
        handoverDocument.setDestination(request.getDestination());
        handoverDocument.setDeparture(request.getDeparture());
        handoverDocument.setTotalPrice(request.getTotalPrice());
        handoverDocument.setUpdateAt(LocalDateTime.now());
        handoverDocument.setUpdateBy(users.getId());
        handoverDocumentRepository.save(handoverDocument);
        return convertToHandoverDocumentResponse(handoverDocument);
    }

    public HandoverDocumentResponse delete(Integer id){
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        HandoverDocument handoverDocument = handoverDocumentRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        handoverDocument.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        handoverDocument.setUpdateAt(LocalDateTime.now());
        handoverDocument.setUpdateBy(users.getId());
        handoverDocumentRepository.save(handoverDocument);
        return convertToHandoverDocumentResponse(handoverDocument);
    }

    public List<HandoverDocumentResponse> getAll() {
        List<HandoverDocument> handoverDocumentList = handoverDocumentRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        List<HandoverDocumentResponse> handoverDocumentResponses = new ArrayList<>();
        for (HandoverDocument handoverDocument : handoverDocumentList) {
            handoverDocumentResponses.add(convertToHandoverDocumentResponse(handoverDocument));
        }
        return handoverDocumentResponses;
    }


    public HandoverDocumentResponse convertToHandoverDocumentResponse(HandoverDocument handoverDocument) {
        return HandoverDocumentResponse.builder()
                .id(handoverDocument.getId())
                .handoverNo(handoverDocument.getHandoverNo())
                .userId(handoverDocument.getUsers().getId())
                .packageId(handoverDocument.getPackages().getId())
                .orderId(handoverDocument.getOrders().getId())
                .handoverDescription(handoverDocument.getHandoverDescription())
                .vehicle(handoverDocument.getVehicle())
                .destination(handoverDocument.getDestination())
                .departure(handoverDocument.getDeparture())
                .totalPrice(handoverDocument.getTotalPrice())
                .createAt(handoverDocument.getCreateAt())
                .createBy(handoverDocument.getCreateBy())
                .updateAt(handoverDocument.getUpdateAt())
                .updateBy(handoverDocument.getUpdateBy())
                .status(handoverDocument.getStatus())
                .build();
    }

    private String generateHandoverNo() {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder;
        String handoverNo = "";
        do {
            stringBuilder = new StringBuilder("HDN");
            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(5);
                stringBuilder.append(RANDOM_STRING.charAt(randomIndex));
            }
            handoverNo = stringBuilder.toString();
        } while (orderRepository.existsByOrderCode(handoverNo));
        return handoverNo;
    }
}
