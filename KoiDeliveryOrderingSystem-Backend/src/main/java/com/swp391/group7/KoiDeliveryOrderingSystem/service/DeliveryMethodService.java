package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.DeliveryMethod;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod.CreateDeliveryMethodRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod.UpdateDeliveryMethodRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.DeliveryMethodResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.DeliveryMethodRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor

public class DeliveryMethodService {
    @Autowired
    private DeliveryMethodRepository deliveryMethodRepository;
    @Autowired
    private AccountUtils accountUtils;

    public DeliveryMethodResponse createDeliveryMethod(CreateDeliveryMethodRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        DeliveryMethod deliveryMethod = DeliveryMethod.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .build();
        deliveryMethodRepository.save(deliveryMethod);
        return convertToDeliveryMethodResponse(deliveryMethod);
    }

    public DeliveryMethodResponse updateDeliveryMethod(Integer id, UpdateDeliveryMethodRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND));
        deliveryMethod.setName(request.getName());
        deliveryMethod.setDescription(request.getDescription());
        deliveryMethod.setPrice(request.getPrice());
        deliveryMethod.setUpdateAt(LocalDateTime.now());
        deliveryMethod.setUpdateBy(users.getId());
        deliveryMethodRepository.save(deliveryMethod);
        return convertToDeliveryMethodResponse(deliveryMethod);
    }

    public List<DeliveryMethodResponse> viewAllDeliveryMethods() {
        List<DeliveryMethod> deliveryMethods = deliveryMethodRepository.findAll();
        List<DeliveryMethodResponse> deliveryMethodResponses = new ArrayList<>();
        for (DeliveryMethod deliveryMethod : deliveryMethods) {
            deliveryMethodResponses.add(convertToDeliveryMethodResponse(deliveryMethod));
        }
        return deliveryMethodResponses;
    }

    public void deleteDeliveryMethod(Integer id) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND));
        deliveryMethodRepository.delete(deliveryMethod);
    }

    public DeliveryMethodResponse convertToDeliveryMethodResponse(DeliveryMethod deliveryMethod) {
        return DeliveryMethodResponse.builder()
                .id(deliveryMethod.getId())
                .name(deliveryMethod.getName())
                .description(deliveryMethod.getDescription())
                .price(deliveryMethod.getPrice())
                .createAt(deliveryMethod.getCreateAt())
                .createBy(deliveryMethod.getCreateBy())
                .updateAt(deliveryMethod.getUpdateAt())
                .updateBy(deliveryMethod.getUpdateBy())
                .build();
    }
}

