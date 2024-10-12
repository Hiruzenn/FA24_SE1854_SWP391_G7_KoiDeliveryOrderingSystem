package com.swp391.group7.KoiDeliveryOrderingSystem.service;


import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.CreateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.HandoverDocumentDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HandoverDocument;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HandoverDocumentRepository;
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
public class HandoverDocumentService {

    @Autowired
    private HandoverDocumentRepository handoverDocumentRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Retrieve the list of all handover documents
    public List<HandoverDocumentDTO> getListHandoverDocuments() {
        List<HandoverDocument> handoverDocumentList = handoverDocumentRepository.findAll();
        if (handoverDocumentList.isEmpty()) {
            throw new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND);
        }
        return handoverDocumentList.stream()
                .map(handoverDocument -> modelMapper.map(handoverDocument, HandoverDocumentDTO.class))
                .toList();
    }

    // Retrieve a specific handover document by its ID
    public HandoverDocumentDTO getHandoverDocumentById(int id) {
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        return modelMapper.map(handoverDocument, HandoverDocumentDTO.class);
    }

    // Create a new handover document
    public ApiResponse<HandoverDocumentDTO> createHandoverDocument(CreateHandoverDocumentRequest handoverDocumentRequest) {
        // Map the CreateHandoverDocumentRequest to HandoverDocument entity
        HandoverDocument handoverDocument = modelMapper.map(handoverDocumentRequest, HandoverDocument.class);

        // Save the new handover document
        handoverDocument = handoverDocumentRepository.save(handoverDocument);

        // Map the saved document back to HandoverDocumentDTO
        HandoverDocumentDTO createdHandoverDocumentDTO = modelMapper.map(handoverDocument, HandoverDocumentDTO.class);

        // Build and return the ApiResponse with the created document
        return ApiResponse.<HandoverDocumentDTO>builder()
                .code(HttpStatus.CREATED.value()) // HTTP status code for created
                .message("Handover document created successfully")
                .result(createdHandoverDocumentDTO)
                .build();
    }

    // Update an existing handover document by ID
    public ApiResponse<HandoverDocumentDTO> updateHandoverDocument(Integer id, CreateHandoverDocumentRequest handoverDocumentRequest) {
        // Check if the handover document exists
        HandoverDocument existingHandoverDocument = handoverDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));

        // Update the existing document with new values
        modelMapper.map(handoverDocumentRequest, existingHandoverDocument);

        // Save the updated handover document
        existingHandoverDocument = handoverDocumentRepository.save(existingHandoverDocument);

        // Map the saved document back to HandoverDocumentDTO
        HandoverDocumentDTO updatedHandoverDocumentDTO = modelMapper.map(existingHandoverDocument, HandoverDocumentDTO.class);

        // Build and return the ApiResponse with the updated document
        return ApiResponse.<HandoverDocumentDTO>builder()
                .code(HttpStatus.OK.value()) // HTTP status code for success
                .message("Handover document updated successfully")
                .result(updatedHandoverDocumentDTO)
                .build();
    }

    // Delete a handover document by ID
    public ApiResponse<Void> deleteHandoverDocument(Integer id) {
        // Check if the handover document exists
        if (!handoverDocumentRepository.existsById(id)) {
            throw new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND);
        }

        // Delete the document
        handoverDocumentRepository.deleteById(id);

        // Build and return the ApiResponse indicating success
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value()) // HTTP status code for no content (successful deletion)
                .message("Handover document deleted successfully")
                .result(null) // No result for deletion
                .build();
    }
}
