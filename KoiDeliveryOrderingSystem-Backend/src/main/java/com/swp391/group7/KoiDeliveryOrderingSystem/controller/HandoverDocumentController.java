package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.HandoverDocumentDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.CreateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<HandoverDocumentDTO>> getHandoverDocumentById(@PathVariable int id) {
        try {
            HandoverDocumentDTO documentDTO = handoverDocumentService.getHandoverDocumentById(id);
            ApiResponse<HandoverDocumentDTO> response = ApiResponse.<HandoverDocumentDTO>builder()
                    .code(200)
                    .message("Handover document retrieved successfully")
                    .result(documentDTO)
                    .build();
            return ResponseEntity.ok(response);
        } catch (AppException e) {
            ApiResponse<HandoverDocumentDTO> response = ApiResponse.<HandoverDocumentDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // POST: Create a new handover document
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<HandoverDocumentDTO>> createHandoverDocument(@RequestBody CreateHandoverDocumentRequest handoverDocumentRequest) {
        try {
            ApiResponse<HandoverDocumentDTO> createdDocumentResponse = handoverDocumentService.createHandoverDocument(handoverDocumentRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDocumentResponse);
        } catch (AppException e) {
            ApiResponse<HandoverDocumentDTO> response = ApiResponse.<HandoverDocumentDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<HandoverDocumentDTO> response = ApiResponse.<HandoverDocumentDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PUT: Update an existing handover document by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<HandoverDocumentDTO>> updateHandoverDocument(
            @PathVariable Integer id,
            @RequestBody CreateHandoverDocumentRequest handoverDocumentRequest) {
        try {
            ApiResponse<HandoverDocumentDTO> updatedDocument = handoverDocumentService.updateHandoverDocument(id, handoverDocumentRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updatedDocument);
        } catch (AppException e) {
            ApiResponse<HandoverDocumentDTO> response = ApiResponse.<HandoverDocumentDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<HandoverDocumentDTO> response = ApiResponse.<HandoverDocumentDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE: Delete a handover document by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHandoverDocument(@PathVariable Integer id) {
        try {
            ApiResponse<Void> response = handoverDocumentService.deleteHandoverDocument(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (AppException e) {
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
