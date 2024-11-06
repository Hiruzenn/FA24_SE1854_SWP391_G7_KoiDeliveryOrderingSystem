package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.InvoiceStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HandoverDocument;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Invoice;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CreateHealthCareDeliveryHistoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthCareDeliveryHistory;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthCareDeliveryHistoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HandoverDocumentRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HealthCareDeliveryHistoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.InvoiceRepository;
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
    InvoiceRepository invoiceRepository;
    @Autowired
    HandoverDocumentRepository handoverDocumentRepository;
    @Autowired
    AccountUtils accountUtils;

    // Retrieve the list of all healthcare delivery histories
    public List<HealthCareDeliveryHistoryResponse> getListHealthCareDeliveryHistories() {
        List<HealthCareDeliveryHistory> healthCareDeliveryHistories = healthCareDeliveryHistoryRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        if (healthCareDeliveryHistories.isEmpty()) {
            throw new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND);
        }
        return covertToListHealthCareDeliveryHistoryResponse(healthCareDeliveryHistories);
    }

    // Retrieve a specific healthcare delivery history by its ID
    public HealthCareDeliveryHistoryResponse getHealthCareDeliveryHistoryById(int id) {
        HealthCareDeliveryHistory healthCareDeliveryHistory = healthCareDeliveryHistoryRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));
        return covertToHealthCareDeliveryHistoryResponse(healthCareDeliveryHistory);
    }

    // Create a new healthcare delivery history
    public HealthCareDeliveryHistoryResponse createHealthCareDeliveryHistory(
            CreateHealthCareDeliveryHistoryRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        // Retrieve the associated Invoice and HandoverDocument
        Invoice invoice = invoiceRepository.findByIdAndStatus(request.getInvoiceId(), InvoiceStatusEnum.UNPAID)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));
        HandoverDocument document = handoverDocumentRepository.findByIdAndStatus(request.getHandoverDocumentId(), SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));

        // Create a new HealthCareDeliveryHistory instance using the builder
        HealthCareDeliveryHistory healthCareDeliveryHistory = HealthCareDeliveryHistory.builder()
                .invoice(invoice) // Set the associated invoice
                .handoverDocument(document) // Set the associated handover document
                .route(request.getRoute()) // Set other fields from the request
                .healthDescription(request.getHealthDescription())
                .eatingDescription(request.getEatingDescription())
                .deliveryStatus(request.getDeliveryStatus())
                .createAt(LocalDateTime.now()) // Set created timestamp
                .createBy(users.getId()) // Set creator ID
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE) // Set initial status
                .build();

        // Save the new health care delivery history to the repository
        healthCareDeliveryHistoryRepository.save(healthCareDeliveryHistory);
        return covertToHealthCareDeliveryHistoryResponse(healthCareDeliveryHistory);
    }

    // Update an existing healthcare delivery history by ID
    public HealthCareDeliveryHistoryResponse updateHealthCareDeliveryHistory(Integer id, CreateHealthCareDeliveryHistoryRequest healthCareDeliveryHistoryRequest) {
        // Check if the healthcare delivery history exists
        HealthCareDeliveryHistory existingHistory = healthCareDeliveryHistoryRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));

        existingHistory.setDeliveryStatus(healthCareDeliveryHistoryRequest.getDeliveryStatus());
        existingHistory.setHealthDescription(healthCareDeliveryHistoryRequest.getHealthDescription());
        existingHistory.setEatingDescription(healthCareDeliveryHistoryRequest.getEatingDescription());
        existingHistory.setRoute(healthCareDeliveryHistoryRequest.getRoute());
        existingHistory.setUpdateAt(LocalDateTime.now());
        existingHistory.setUpdateBy(accountUtils.getCurrentUser().getId());

        // Save the updated healthcare delivery history
        existingHistory = healthCareDeliveryHistoryRepository.save(existingHistory);
        return covertToHealthCareDeliveryHistoryResponse(existingHistory);
    }

    // Delete a healthcare delivery history by ID

    public HealthCareDeliveryHistoryResponse removeHealthCareDeliveryHistory(Integer id) {
        // Check if the healthcare delivery history exists
        HealthCareDeliveryHistory existingHistory = healthCareDeliveryHistoryRepository.findByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));

        existingHistory.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        existingHistory.setUpdateAt(LocalDateTime.now());
        existingHistory.setUpdateBy(accountUtils.getCurrentUser().getId());

        // Save the updated healthcare delivery history
        existingHistory = healthCareDeliveryHistoryRepository.save(existingHistory);
        return covertToHealthCareDeliveryHistoryResponse(existingHistory);
    }

    public List<HealthCareDeliveryHistoryResponse> covertToListHealthCareDeliveryHistoryResponse(List<HealthCareDeliveryHistory> healthCareDeliveryHistoryList) {
        List<HealthCareDeliveryHistoryResponse> historyResponses = new ArrayList<>();
        for (HealthCareDeliveryHistory healthCareDeliveryHistory : healthCareDeliveryHistoryList) {
            historyResponses.add(covertToHealthCareDeliveryHistoryResponse(healthCareDeliveryHistory));
        }
        return historyResponses;
    }

    public HealthCareDeliveryHistoryResponse covertToHealthCareDeliveryHistoryResponse(HealthCareDeliveryHistory healthCareDeliveryHistory) {
        if (healthCareDeliveryHistory == null) {
            return null; // Handle null input if necessary
        }

        return HealthCareDeliveryHistoryResponse.builder()
                .id(healthCareDeliveryHistory.getId())
                .invoiceId(healthCareDeliveryHistory.getInvoice().getId()) // Ensure proper mapping if necessary
                .handoverDocumentId(healthCareDeliveryHistory.getHandoverDocument().getId()) // Ensure proper mapping if necessary
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
