package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HandoverDocument;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CreateHealthCareDeliveryHistoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthCareDeliveryHistory;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthCareDeliveryHistoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HandoverDocumentRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HealthCareDeliveryHistoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
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
public class HealthCareDeliveryHistoryService {

    @Autowired
    private HealthCareDeliveryHistoryRepository healthCareDeliveryHistoryRepository;

    @Autowired
    HandoverDocumentRepository handoverDocumentRepository;

    @Autowired
    AccountUtils accountUtils;
    @Autowired
    private OrderRepository orderRepository;

    // Retrieve the list of all healthcare delivery histories
    public List<HealthCareDeliveryHistoryResponse> viewAll() {
        List<HealthCareDeliveryHistory> healthCareDeliveryHistories = healthCareDeliveryHistoryRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        if (healthCareDeliveryHistories.isEmpty()) {
            throw new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND);
        }
        return convertToListHealthCareDeliveryHistoryResponse(healthCareDeliveryHistories);
    }

    // Retrieve a specific healthcare delivery history by its ID
    public HealthCareDeliveryHistoryResponse viewById(int id) {
        HealthCareDeliveryHistory healthCareDeliveryHistory = healthCareDeliveryHistoryRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));
        return convertToHealthCareDeliveryHistoryResponse(healthCareDeliveryHistory);
    }

    public List<HealthCareDeliveryHistoryResponse> viewByHandoverDocument(Integer handoverDocumentId) {
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(handoverDocumentId)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        List<HealthCareDeliveryHistory> healthCareDeliveryHistories = healthCareDeliveryHistoryRepository
                .findByHandoverDocumentAndStatus(handoverDocument, SystemStatusEnum.AVAILABLE);
        return convertToListHealthCareDeliveryHistoryResponse(healthCareDeliveryHistories);
    }

    public HealthCareDeliveryHistoryResponse create(Integer orderId, CreateHealthCareDeliveryHistoryRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.IN_PROGRESS)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        HandoverDocument handoverDocument = handoverDocumentRepository.findByOrders(orders)
                .orElseThrow(()-> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        HealthCareDeliveryHistory healthCareDeliveryHistory = HealthCareDeliveryHistory.builder()
                .handoverDocument(handoverDocument)
                .route(request.getRoute())
                .healthDescription(request.getHealthDescription())
                .eatingDescription(request.getEatingDescription())
                .deliveryStatus(request.getDeliveryStatus())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();

        healthCareDeliveryHistoryRepository.save(healthCareDeliveryHistory);
        return convertToHealthCareDeliveryHistoryResponse(healthCareDeliveryHistory);
    }


    public HealthCareDeliveryHistoryResponse update(Integer id, CreateHealthCareDeliveryHistoryRequest healthCareDeliveryHistoryRequest) {

        HealthCareDeliveryHistory existingHistory = healthCareDeliveryHistoryRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));

        existingHistory.setDeliveryStatus(healthCareDeliveryHistoryRequest.getDeliveryStatus());
        existingHistory.setHealthDescription(healthCareDeliveryHistoryRequest.getHealthDescription());
        existingHistory.setEatingDescription(healthCareDeliveryHistoryRequest.getEatingDescription());
        existingHistory.setRoute(healthCareDeliveryHistoryRequest.getRoute());
        existingHistory.setUpdateAt(LocalDateTime.now());
        existingHistory.setUpdateBy(accountUtils.getCurrentUser().getId());

        existingHistory = healthCareDeliveryHistoryRepository.save(existingHistory);
        return convertToHealthCareDeliveryHistoryResponse(existingHistory);
    }


    public HealthCareDeliveryHistoryResponse remove(Integer id) {
        HealthCareDeliveryHistory existingHistory = healthCareDeliveryHistoryRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));

        existingHistory.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        existingHistory.setUpdateAt(LocalDateTime.now());
        existingHistory.setUpdateBy(accountUtils.getCurrentUser().getId());

        existingHistory = healthCareDeliveryHistoryRepository.save(existingHistory);
        return convertToHealthCareDeliveryHistoryResponse(existingHistory);
    }

    public List<HealthCareDeliveryHistoryResponse> convertToListHealthCareDeliveryHistoryResponse(List<HealthCareDeliveryHistory> healthCareDeliveryHistoryList) {
        List<HealthCareDeliveryHistoryResponse> historyResponses = new ArrayList<>();
        for (HealthCareDeliveryHistory healthCareDeliveryHistory : healthCareDeliveryHistoryList) {
            historyResponses.add(convertToHealthCareDeliveryHistoryResponse(healthCareDeliveryHistory));
        }
        return historyResponses;
    }

    public HealthCareDeliveryHistoryResponse convertToHealthCareDeliveryHistoryResponse(HealthCareDeliveryHistory healthCareDeliveryHistory) {
        return HealthCareDeliveryHistoryResponse.builder()
                .id(healthCareDeliveryHistory.getId())
                .handoverDocumentId(healthCareDeliveryHistory.getHandoverDocument().getId())
                .route(healthCareDeliveryHistory.getRoute())
                .healthDescription(healthCareDeliveryHistory.getHealthDescription())
                .eatingDescription(healthCareDeliveryHistory.getEatingDescription())
                .deliveryStatus(healthCareDeliveryHistory.getDeliveryStatus())
                .createAt(healthCareDeliveryHistory.getCreateAt())
                .createBy(healthCareDeliveryHistory.getCreateBy())
                .updateAt(healthCareDeliveryHistory.getUpdateAt())
                .updateBy(healthCareDeliveryHistory.getUpdateBy())
                .status(healthCareDeliveryHistory.getStatus())
                .build();
    }
}
