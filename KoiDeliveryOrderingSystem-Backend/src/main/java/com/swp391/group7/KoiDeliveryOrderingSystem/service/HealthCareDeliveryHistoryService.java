package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
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
    private HandoverDocumentRepository handoverDocumentRepository;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private OrderRepository orderRepository;

    // Retrieve the list of all healthcare delivery histories
    public List<HealthCareDeliveryHistoryResponse> viewAll() {
        List<HealthCareDeliveryHistory> healthCareDeliveryHistories = healthCareDeliveryHistoryRepository.findAll();
        return convertToListHealthCareDeliveryHistoryResponse(healthCareDeliveryHistories);
    }

    // Retrieve a specific healthcare delivery history by its ID
    public HealthCareDeliveryHistoryResponse viewById(Integer id) {
        HealthCareDeliveryHistory healthCareDeliveryHistory = healthCareDeliveryHistoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));
        return convertToHealthCareDeliveryHistoryResponse(healthCareDeliveryHistory);
    }

    public List<HealthCareDeliveryHistoryResponse> viewByHandoverDocument(Integer handoverDocumentId) {
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(handoverDocumentId)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        List<HealthCareDeliveryHistory> healthCareDeliveryHistories = healthCareDeliveryHistoryRepository
                .findByHandoverDocument(handoverDocument);
        return convertToListHealthCareDeliveryHistoryResponse(healthCareDeliveryHistories);
    }

    public HealthCareDeliveryHistoryResponse create(Integer handoverDocumentId, CreateHealthCareDeliveryHistoryRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(handoverDocumentId)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        HealthCareDeliveryHistory healthCareDeliveryHistory = HealthCareDeliveryHistory.builder()
                .handoverDocument(handoverDocument)
                .route(request.getRoute())
                .healthDescription(request.getHealthDescription())
                .eatingDescription(request.getEatingDescription())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .build();

        healthCareDeliveryHistoryRepository.save(healthCareDeliveryHistory);
        return convertToHealthCareDeliveryHistoryResponse(healthCareDeliveryHistory);
    }


    public HealthCareDeliveryHistoryResponse update(Integer id, CreateHealthCareDeliveryHistoryRequest request) {

        HealthCareDeliveryHistory history = healthCareDeliveryHistoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));

        history.setHealthDescription(request.getHealthDescription());
        history.setEatingDescription(request.getEatingDescription());
        history.setRoute(request.getRoute());
        history.setUpdateAt(LocalDateTime.now());
        history.setUpdateBy(accountUtils.getCurrentUser().getId());

        history = healthCareDeliveryHistoryRepository.save(history);
        return convertToHealthCareDeliveryHistoryResponse(history);
    }


    public void remove(Integer id) {
        HealthCareDeliveryHistory history = healthCareDeliveryHistoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));
        healthCareDeliveryHistoryRepository.delete(history);
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
                .createAt(healthCareDeliveryHistory.getCreateAt())
                .createBy(healthCareDeliveryHistory.getCreateBy())
                .updateAt(healthCareDeliveryHistory.getUpdateAt())
                .updateBy(healthCareDeliveryHistory.getUpdateBy())
                .build();
    }
}
