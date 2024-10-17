package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HandoverDocument;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Invoice;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CreateHealthCareDeliveryHistoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.HealthCareDeliveryHistoryDTO;
    import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthCareDeliveryHistory;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HandoverDocumentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthCareDeliveryHistoryResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HandoverDocumentRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HealthCareDeliveryHistoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.InvoiceRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ModelMapper modelMapper;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    HandoverDocumentRepository handoverDocumentRepository;
    @Autowired
    AccountUtils accountUtils;

    // Retrieve the list of all healthcare delivery histories
    public List<HealthCareDeliveryHistoryResponse> getListHealthCareDeliveryHistories() {
        List<HealthCareDeliveryHistory> healthCareDeliveryHistories = healthCareDeliveryHistoryRepository.findAll();
        if (healthCareDeliveryHistories.isEmpty()) {
            throw new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND);
        }
        return covertToListHealthCareDeliveryHistoryResponse(healthCareDeliveryHistories);
    }

    // Retrieve a specific healthcare delivery history by its ID
    public HealthCareDeliveryHistoryResponse getHealthCareDeliveryHistoryById(int id) {
        HealthCareDeliveryHistory healthCareDeliveryHistory = healthCareDeliveryHistoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));
        return covertToHealthCareDeliveryHistoryResponse(healthCareDeliveryHistory);
    }

    // Create a new healthcare delivery history
    public HealthCareDeliveryHistoryResponse createHealthCareDeliveryHistory(
            CreateHealthCareDeliveryHistoryRequest healthCareDeliveryHistoryRequest,
            int handoverDocumentId,
            int invoiceId) {

        // Retrieve the associated Invoice and HandoverDocument
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));
        HandoverDocument document = handoverDocumentRepository.findById(handoverDocumentId)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));

        // Create a new HealthCareDeliveryHistory instance using the builder
        HealthCareDeliveryHistory healthCareDeliveryHistory = HealthCareDeliveryHistory.builder()
                .invoice(invoice) // Set the associated invoice
                .handoverDocument(document) // Set the associated handover document
                .route(healthCareDeliveryHistoryRequest.getRoute()) // Set other fields from the request
                .healthDescription(healthCareDeliveryHistoryRequest.getHealthDescription())
                .eatingDescription(healthCareDeliveryHistoryRequest.getEatingDescription())
                .deliveryStatus(healthCareDeliveryHistoryRequest.getDeliveryStatus())
                .createAt(LocalDateTime.now()) // Set created timestamp
                .createBy(accountUtils.getCurrentUser().getId()) // Set creator ID
                .status(SystemStatusEnum.NOT_AVAILABLE) // Set initial status
                .build();

        // Save the new health care delivery history to the repository
        healthCareDeliveryHistory = healthCareDeliveryHistoryRepository.save(healthCareDeliveryHistory);


        return covertToHealthCareDeliveryHistoryResponse(healthCareDeliveryHistory);
    }

    // Update an existing healthcare delivery history by ID
    public HealthCareDeliveryHistoryResponse updateHealthCareDeliveryHistory(Integer id, CreateHealthCareDeliveryHistoryRequest healthCareDeliveryHistoryRequest) {
        // Check if the healthcare delivery history exists
        HealthCareDeliveryHistory existingHistory = healthCareDeliveryHistoryRepository.findById(id)
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
        HealthCareDeliveryHistory existingHistory = healthCareDeliveryHistoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));

        existingHistory.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        existingHistory.setUpdateAt(LocalDateTime.now());
        existingHistory.setUpdateBy(accountUtils.getCurrentUser().getId());

        // Save the updated healthcare delivery history
        existingHistory = healthCareDeliveryHistoryRepository.save(existingHistory);
        return covertToHealthCareDeliveryHistoryResponse(existingHistory);
    }
    public List<HealthCareDeliveryHistoryResponse> covertToListHealthCareDeliveryHistoryResponse(List<HealthCareDeliveryHistory> healthCareDeliveryHistoryList)
    {
        List<HealthCareDeliveryHistoryResponse> historyResponses= new ArrayList<>();
        for (HealthCareDeliveryHistory healthCareDeliveryHistory: healthCareDeliveryHistoryList)
        {
            historyResponses.add(covertToHealthCareDeliveryHistoryResponse(healthCareDeliveryHistory));
        }
        return historyResponses;
    }

    public HealthCareDeliveryHistoryResponse covertToHealthCareDeliveryHistoryResponse(HealthCareDeliveryHistory healthCareDeliveryHistory){
        if (healthCareDeliveryHistory == null) {
            return null; // Handle null input if necessary
        }

        return HealthCareDeliveryHistoryResponse.builder()
                .id(healthCareDeliveryHistory.getId())
                .invoice(healthCareDeliveryHistory.getInvoice()) // Ensure proper mapping if necessary
                .handoverDocument(healthCareDeliveryHistory.getHandoverDocument()) // Ensure proper mapping if necessary
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
