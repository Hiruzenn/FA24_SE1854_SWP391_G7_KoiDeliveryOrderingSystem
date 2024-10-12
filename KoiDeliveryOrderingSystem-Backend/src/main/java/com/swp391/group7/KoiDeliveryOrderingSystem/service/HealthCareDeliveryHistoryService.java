package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CreateHealthCareDeliveryHistoryRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.HealthCareDeliveryHistoryDTO;
    import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthCareDeliveryHistory;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HealthCareDeliveryHistoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HealthCareDeliveryHistoryService {

    @Autowired
    private HealthCareDeliveryHistoryRepository healthCareDeliveryHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Retrieve the list of all healthcare delivery histories
    public List<HealthCareDeliveryHistoryDTO> getListHealthCareDeliveryHistories() {
        List<HealthCareDeliveryHistory> healthCareDeliveryHistories = healthCareDeliveryHistoryRepository.findAll();
        if (healthCareDeliveryHistories.isEmpty()) {
            throw new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND);
        }
        return healthCareDeliveryHistories.stream()
                .map(history -> modelMapper.map(history, HealthCareDeliveryHistoryDTO.class))
                .toList();
    }

    // Retrieve a specific healthcare delivery history by its ID
    public HealthCareDeliveryHistoryDTO getHealthCareDeliveryHistoryById(int id) {
        HealthCareDeliveryHistory healthCareDeliveryHistory = healthCareDeliveryHistoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));
        return modelMapper.map(healthCareDeliveryHistory, HealthCareDeliveryHistoryDTO.class);
    }

    // Create a new healthcare delivery history
    public ApiResponse<HealthCareDeliveryHistoryDTO> createHealthCareDeliveryHistory(CreateHealthCareDeliveryHistoryRequest healthCareDeliveryHistoryRequest) {
        // Map the request to HealthCareDeliveryHistory entity
        HealthCareDeliveryHistory healthCareDeliveryHistory = modelMapper.map(healthCareDeliveryHistoryRequest, HealthCareDeliveryHistory.class);

        // Save the new healthcare delivery history
        healthCareDeliveryHistory = healthCareDeliveryHistoryRepository.save(healthCareDeliveryHistory);

        // Map the saved history to HealthCareDeliveryHistoryDTO
        HealthCareDeliveryHistoryDTO createdHistoryDTO = modelMapper.map(healthCareDeliveryHistory, HealthCareDeliveryHistoryDTO.class);

        // Build and return the ApiResponse with the created history
        return ApiResponse.<HealthCareDeliveryHistoryDTO>builder()
                .code(HttpStatus.CREATED.value()) // HTTP status code for created
                .message("HealthCare delivery history created successfully")
                .result(createdHistoryDTO)
                .build();
    }

    // Update an existing healthcare delivery history by ID
    public ApiResponse<HealthCareDeliveryHistoryDTO> updateHealthCareDeliveryHistory(Integer id, CreateHealthCareDeliveryHistoryRequest healthCareDeliveryHistoryRequest) {
        // Check if the healthcare delivery history exists
        HealthCareDeliveryHistory existingHistory = healthCareDeliveryHistoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND));

        // Update the existing history with new values
        modelMapper.map(healthCareDeliveryHistoryRequest, existingHistory);

        // Save the updated healthcare delivery history
        existingHistory = healthCareDeliveryHistoryRepository.save(existingHistory);

        // Map the saved history to HealthCareDeliveryHistoryDTO
        HealthCareDeliveryHistoryDTO updatedHistoryDTO = modelMapper.map(existingHistory, HealthCareDeliveryHistoryDTO.class);

        // Build and return the ApiResponse with the updated history
        return ApiResponse.<HealthCareDeliveryHistoryDTO>builder()
                .code(HttpStatus.OK.value()) // HTTP status code for success
                .message("HealthCare delivery history updated successfully")
                .result(updatedHistoryDTO)
                .build();
    }

    // Delete a healthcare delivery history by ID
    public ApiResponse<Void> deleteHealthCareDeliveryHistory(Integer id) {
        // Check if the healthcare delivery history exists
        if (!healthCareDeliveryHistoryRepository.existsById(id)) {
            throw new AppException(ErrorCode.HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND);
        }

        // Delete the history
        healthCareDeliveryHistoryRepository.deleteById(id);

        // Build and return the ApiResponse indicating success
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value()) // HTTP status code for no content (successful deletion)
                .message("HealthCare delivery history deleted successfully")
                .result(null) // No result for deletion
                .build();
    }
}
