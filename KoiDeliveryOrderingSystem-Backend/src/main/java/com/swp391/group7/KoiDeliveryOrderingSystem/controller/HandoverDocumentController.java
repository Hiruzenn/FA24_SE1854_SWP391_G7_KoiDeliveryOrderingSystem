package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.HandoverDocumentDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.CreateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HandoverDocumentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.HandoverDocumentService;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/handover-documents")
@RequiredArgsConstructor
public class HandoverDocumentController {

    private final HandoverDocumentService handoverDocumentService;

    // GET: Retrieve the list of handover documents
    @GetMapping
    public ResponseEntity<ApiResponse<List<HandoverDocumentDTO>>> getAllHandoverDocuments() {
        List<HandoverDocumentDTO> documents = handoverDocumentService.getListHandoverDocuments();

        ApiResponse<List<HandoverDocumentDTO>> response = ApiResponse.<List<HandoverDocumentDTO>>builder()
                .code(200)
                .message("Handover documents retrieved successfully")
                .result(documents)
                .build();

        return ResponseEntity.ok(response);
    }

    // GET: Retrieve a specific handover document by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HandoverDocumentResponse>> getHandoverDocumentById(@PathVariable int id) {
        try {
            HandoverDocumentResponse documentResponse = handoverDocumentService.getHandoverDocumentById(id);

            // Create the successful response
            ApiResponse<HandoverDocumentResponse> response = ApiResponse.<HandoverDocumentResponse>builder()
                    .code(HttpStatus.OK.value()) // Use HttpStatus.OK for clarity
                    .message("Handover document retrieved successfully")
                    .result(documentResponse)
                    .build();

            return ResponseEntity.ok(response);
        } catch (AppException e) {
            // Create the error response
            ApiResponse<HandoverDocumentResponse> response = ApiResponse.<HandoverDocumentResponse>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null) // Keep the result as null for errors
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // POST: Create a new handover document
    @PostMapping("/create")
    public ApiResponse<HandoverDocumentResponse> createHandoverDocument(
            @RequestBody CreateHandoverDocumentRequest handoverDocumentRequest,
            @PathVariable int orderId,
            @PathVariable int packageId) {

            HandoverDocumentResponse createdDocumentResponse = handoverDocumentService.createHandoverDocument(handoverDocumentRequest, orderId, packageId);
            return ApiResponse.<HandoverDocumentResponse>builder()
                    .code(200)
                    .message("Handover Document created successfully")
                    .result(createdDocumentResponse)
                    .build();

    }

    // PUT: Update an existing handover document by ID
    @PutMapping("/update/{id}")
    public ApiResponse<HandoverDocumentResponse> updateHandoverDocument(
            @PathVariable Integer id,
            @RequestBody CreateHandoverDocumentRequest handoverDocumentRequest) {
        HandoverDocumentResponse updatedDocumentResponse = handoverDocumentService.updateHandoverDocument(id , handoverDocumentRequest);
        return ApiResponse.<HandoverDocumentResponse>builder()
                .code(200)
                .message("Handover Document updated successfully")
                .result(updatedDocumentResponse)
                .build();
    }

    // DELETE: Delete a handover document by ID
    @PutMapping("/delete/{id}")
    public ApiResponse<HandoverDocumentResponse> deleteHandoverDocument(@PathVariable Integer id) {
        HandoverDocumentResponse documentResponse= handoverDocumentService.removeHandoverDocument(id);
        return ApiResponse.<HandoverDocumentResponse>builder()
                .code(200)
                .message("Handover Document removed successfully")
                .result(documentResponse)
                .build();
    }
}
