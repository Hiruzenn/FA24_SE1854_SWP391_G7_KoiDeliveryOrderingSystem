package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.CustomsDeclaration;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.customsdeclaration.CreateCustomsDeclarationRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CustomsDeclarationResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CustomsDeclarationRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomsDeclarationService {
    @Autowired
    private CustomsDeclarationRepository customsDeclarationRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountUtils accountUtils;

    public CustomsDeclarationResponse createCustomsDeclaration(Integer orderId, CreateCustomsDeclarationRequest createCustomsDeclarationRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.AVAILABLE).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        CustomsDeclaration customsDeclaration = CustomsDeclaration.builder()
                .customsName(createCustomsDeclarationRequest.getCustomsName())
                .declarationDate(createCustomsDeclarationRequest.getDeclarationDate())
                .declarationNo(createCustomsDeclarationRequest.getDeclarationNo())
                .declarationBy(createCustomsDeclarationRequest.getDeclarationBy())
                .orders(orders)
                .referenceDate(createCustomsDeclarationRequest.getReferenceDate())
                .image(createCustomsDeclarationRequest.getImage())
                .referenceNo(createCustomsDeclarationRequest.getReferenceNo())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        customsDeclarationRepository.save(customsDeclaration);
        return covertToCustomsDeclarationResponse(customsDeclaration);
    }

    public CustomsDeclarationResponse updateCustomsDeclaration(Integer id, CreateCustomsDeclarationRequest customsDeclarationRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        CustomsDeclaration customsDeclaration = customsDeclarationRepository.findCustomsDeclarationByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOM_DECLARATION_NOT_EXISTED));

        customsDeclaration.setDeclarationNo(customsDeclarationRequest.getDeclarationNo());
        customsDeclaration.setCustomsName(customsDeclarationRequest.getCustomsName());
        customsDeclaration.setDeclarationDate(customsDeclarationRequest.getDeclarationDate());
        customsDeclaration.setImage(customsDeclarationRequest.getImage());
        customsDeclaration.setDeclarationBy(customsDeclarationRequest.getDeclarationBy());
        customsDeclaration.setReferenceDate(customsDeclarationRequest.getReferenceDate());
        customsDeclaration.setReferenceNo(customsDeclarationRequest.getReferenceNo());
        customsDeclaration.setUpdateAt(LocalDateTime.now());
        customsDeclaration.setUpdateBy(users.getId());
        customsDeclarationRepository.save(customsDeclaration); // Save and update entity

        return covertToCustomsDeclarationResponse(customsDeclaration);
    }

    public List<CustomsDeclarationResponse> getListCustomDeclaration() {
        List<CustomsDeclaration> customsDeclarationList = customsDeclarationRepository.findCustomsDeclarationsByStatus(SystemStatusEnum.AVAILABLE);
        if (customsDeclarationList.isEmpty()) {
            throw new AppException(ErrorCode.CUSTOM_DECLARATION_NOT_EXISTED);
        }
        return convertToListCustomsDeclarationResponse(customsDeclarationList);
    }

    public CustomsDeclarationResponse getCustomsDeclaration(int id) {
        CustomsDeclaration customsDeclaration = customsDeclarationRepository.findCustomsDeclarationByIdAndStatus(id, SystemStatusEnum.AVAILABLE).
                orElseThrow(() -> new AppException(ErrorCode.CUSTOM_DECLARATION_NOT_EXISTED));
        return covertToCustomsDeclarationResponse(customsDeclaration);
    }

    public CustomsDeclarationResponse getCustomsDeclarationByOrder(Integer orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        CustomsDeclaration customsDeclaration = customsDeclarationRepository.findByOrdersAndStatus(orders, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOM_DECLARATION_NOT_EXISTED));
        return covertToCustomsDeclarationResponse(customsDeclaration);
    }

    public CustomsDeclarationResponse removeCustomDeclaration(int id) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        CustomsDeclaration customsDeclaration = customsDeclarationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOM_DECLARATION_NOT_EXISTED));
        customsDeclaration.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        customsDeclaration.setUpdateAt(LocalDateTime.now());
        customsDeclaration.setUpdateBy(users.getId());
        customsDeclarationRepository.save(customsDeclaration);
        return covertToCustomsDeclarationResponse(customsDeclaration);
    }


    public List<CustomsDeclarationResponse> convertToListCustomsDeclarationResponse(List<CustomsDeclaration> customsDeclarationList) {
        List<CustomsDeclarationResponse> customsDeclarationResponses = new ArrayList<>();
        for (CustomsDeclaration customsDeclaration : customsDeclarationList) {
            customsDeclarationResponses.add(covertToCustomsDeclarationResponse(customsDeclaration));
        }
        return customsDeclarationResponses;
    }

    public CustomsDeclarationResponse covertToCustomsDeclarationResponse(CustomsDeclaration customsDeclaration) {
        return CustomsDeclarationResponse.builder()
                .id(customsDeclaration.getId())
                .orderId(customsDeclaration.getOrders().getId())
                .declarationNo(customsDeclaration.getDeclarationNo())
                .customsName(customsDeclaration.getCustomsName())
                .referenceNo(customsDeclaration.getReferenceNo())
                .declarationDate(customsDeclaration.getDeclarationDate())
                .declarationBy(customsDeclaration.getDeclarationBy())
                .image(customsDeclaration.getImage())
                .referenceDate(customsDeclaration.getReferenceDate())
                .createAt(customsDeclaration.getCreateAt())
                .createBy(customsDeclaration.getCreateBy())
                .updateAt(customsDeclaration.getUpdateAt())
                .updateBy(customsDeclaration.getUpdateBy())
                .status(customsDeclaration.getStatus())
                .build();
    }

}
