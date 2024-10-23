package com.swp391.group7.KoiDeliveryOrderingSystem.controller;


import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.CreateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.UpdateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HandoverDocumentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.HandoverDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/handover-documents")
@RequiredArgsConstructor
public class HandoverDocumentController {

    private final HandoverDocumentService handoverDocumentService;

    // GET: Retrieve the list of handover documents
    @GetMapping("/view-all")
    public ApiResponse<List<HandoverDocumentResponse>> viewAllHandoverDocuments() {
            var result = handoverDocumentService.viewAllHandoverDocuments();
            return ApiResponse.<List<HandoverDocumentResponse>>builder()
                    .code(200)
                    .message("Handover Document Viewed")
                    .result(result)
                    .build();
    }

    // GET: Retrieve a specific handover document by ID
    @GetMapping("/get-by-id/{id}")
    public ApiResponse<HandoverDocumentResponse> getHandoverDocumentById(@PathVariable Integer id) {
        var result = handoverDocumentService.getHandoverDocumentById(id);
        return ApiResponse.<HandoverDocumentResponse>builder()
                .code(200)
                .message("Handover Document Viewed")
                .result(result)
                .build();
    }

    // POST: Create a new handover document
    @PostMapping("/create/{orderId}/{packageId}")
        public ApiResponse<HandoverDocumentResponse> createHandoverDocument(@PathVariable("orderId") Integer orderId,
                                                                            @PathVariable("packageId") Integer packageId,
                                                                            @RequestBody CreateHandoverDocumentRequest createHandoverDocumentRequest) {
        var result = handoverDocumentService.createHandoverDocument(orderId, packageId, createHandoverDocumentRequest);
        return ApiResponse.<HandoverDocumentResponse>builder()
                .code(200)
                .message("Handover Document Created")
                .result(result)
                .build();
    }
        // PUT: Update an existing handover document by ID
        @PutMapping("/update/{id}")
        public ApiResponse<HandoverDocumentResponse> updateHandoverDocument (@PathVariable int id, @RequestBody UpdateHandoverDocumentRequest updateHandoverDocumentRequest){
            var result = handoverDocumentService.updateHandoverDocument(id, updateHandoverDocumentRequest);
            return ApiResponse.<HandoverDocumentResponse>builder()
                    .code(200)
                    .message("Handover Document Updated")
                    .result(result)
                    .build();
        }

        // DELETE: Delete a handover document by ID
        @DeleteMapping("/delete/{id}")
        public ApiResponse<HandoverDocumentResponse> deleteHandoverDocument ( @PathVariable int id){
            var result = handoverDocumentService.deleteHandoverDocument(id);
            return ApiResponse.<HandoverDocumentResponse>builder()
                    .code(200)
                    .message("Handover Document Deleted")
                    .result(result)
                    .build();
        }
    }
