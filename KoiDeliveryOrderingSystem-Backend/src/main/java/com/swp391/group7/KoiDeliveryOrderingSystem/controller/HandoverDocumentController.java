package com.swp391.group7.KoiDeliveryOrderingSystem.controller;


import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.CreateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.UpdateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HandoverDocumentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.HandoverDocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("handover-documents")
@RequiredArgsConstructor
@Tag(name = "Handover Document")
public class HandoverDocumentController {

    private final HandoverDocumentService handoverDocumentService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<HandoverDocumentResponse>> createHandoverDocument(@Valid @RequestBody CreateHandoverDocumentRequest request) {
        var result = handoverDocumentService.create(request);
        return ResponseEntity.ok(ApiResponse.<HandoverDocumentResponse>builder()
                .code(200)
                .message("handover document created")
                .result(result)
                .build());
    }

    @PutMapping("update/{handoverDocumentId}")
    public ResponseEntity<ApiResponse<HandoverDocumentResponse>> updateHandoverDocument(@Valid @PathVariable("handoverDocumentId") Integer handoverDocumentId, @RequestBody UpdateHandoverDocumentRequest request) {
        var result = handoverDocumentService.update(handoverDocumentId, request);
        return ResponseEntity.ok(ApiResponse.<HandoverDocumentResponse>builder()
                .code(200)
                .message("handover document updated")
                .result(result)
                .build());
    }

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<HandoverDocumentResponse>>> viewAll() {
        var result = handoverDocumentService.getAll();
        return ResponseEntity.ok(ApiResponse.<List<HandoverDocumentResponse>>builder()
                .code(200)
                .message("handover document list")
                .result(result)
                .build());
    }

    @GetMapping("view-by-delivery-staff")
    public ResponseEntity<ApiResponse<List<HandoverDocumentResponse>>> viewByDeliveryStaff() {
        var result = handoverDocumentService.viewByDeliveryStaff();
        return ResponseEntity.ok(ApiResponse.<List<HandoverDocumentResponse>>builder()
                .code(200)
                .message("handover document list by current delivery staff")
                .result(result)
                .build());
    }

    @GetMapping("view-by-order/{orderId}")
    public ResponseEntity<ApiResponse<HandoverDocumentResponse>> viewByOrder(@PathVariable Integer orderId) {
        var result = handoverDocumentService.viewByOrder(orderId);
        return ResponseEntity.ok(ApiResponse.<HandoverDocumentResponse>builder()
                .code(200)
                .message("handover document list by current order")
                .result(result)
                .build());
    }

    @GetMapping("view-one/{id}")
    public ResponseEntity<ApiResponse<HandoverDocumentResponse>> viewOne(@PathVariable Integer id) {
        var result = handoverDocumentService.viewOne(id);
        return ResponseEntity.ok(ApiResponse.<HandoverDocumentResponse>builder()
                .code(200)
                .message("handover document list by id")
                .result(result)
                .build());
    }

    @DeleteMapping("delete/{orderId}")
    public ResponseEntity<ApiResponse<Void>> deleteHandoverDocument(@Valid @PathVariable("orderId") Integer orderId) {
        handoverDocumentService.delete(orderId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Handover document deleted")
                .build());
    }
}
