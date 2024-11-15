package com.swp391.group7.KoiDeliveryOrderingSystem.controller;


import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.customsdeclaration.CreateCustomsDeclarationRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CustomsDeclarationResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.CustomsDeclarationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customs-declaration")
@Tag(name = "Customs Declaration")
public class CustomsDeclarationController {
    @Autowired
    CustomsDeclarationService customsDeclarationService;

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<CustomsDeclarationResponse>>> getAllCustomDeclarations() {
        List<CustomsDeclarationResponse> customsDeclarations = customsDeclarationService.getListCustomDeclaration();
        return ResponseEntity.ok(ApiResponse.<List<CustomsDeclarationResponse>>builder()
                .code(200)
                .message("Customs declarations retrieved successfully")
                .result(customsDeclarations)
                .build());
    }

    @GetMapping("view-one/{id}")
    public ResponseEntity<ApiResponse<CustomsDeclarationResponse>> getCustomDeclarationById(@PathVariable Integer id) {
        var result = customsDeclarationService.getCustomsDeclaration(id);
        return ResponseEntity.ok(ApiResponse.<CustomsDeclarationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Customs declaration retrieved successfully")
                .result(result)
                .build());
    }

    @GetMapping("view-by-order/{orderId}")
    public ResponseEntity<ApiResponse<CustomsDeclarationResponse>> getCustomDeclarationByOrderId(@PathVariable("orderId") Integer orderId) {
        var result = customsDeclarationService.getCustomsDeclarationByOrder(orderId);
        return ResponseEntity.ok(ApiResponse.<CustomsDeclarationResponse>builder()
                .code(200)
                .message("Customs Declaration by Order")
                .result(result)
                .build());
    }

    @PostMapping("/create/{orderId}")
    public ResponseEntity<ApiResponse<CustomsDeclarationResponse>> createCustomDeclaration
            (@PathVariable Integer orderId, @RequestBody CreateCustomsDeclarationRequest request) {
        var result = customsDeclarationService.createCustomsDeclaration(orderId, request);
        return ResponseEntity.ok(ApiResponse.<CustomsDeclarationResponse>builder()
                .code(200)
                .message("Customs declaration created successfully")
                .result(result)
                .build());
    }

    @PutMapping("/update/{id}") // Endpoint to update a customs declaration by ID
    public ResponseEntity<ApiResponse<CustomsDeclarationResponse>> updateCustomDeclaration(
            @PathVariable Integer id,
            @RequestBody CreateCustomsDeclarationRequest request) {
        var result = customsDeclarationService.updateCustomsDeclaration(id, request);
        return ResponseEntity.ok(ApiResponse.<CustomsDeclarationResponse>builder()
                .code(200)
                .message("Customs declaration updated successfully")
                .result(result)
                .build());

    }

    @PutMapping("/delete/{id}") // Endpoint to delete a customs declaration by ID
    public ResponseEntity<ApiResponse<CustomsDeclarationResponse>> deleteCustomDeclaration(@PathVariable Integer id) {
        var result = customsDeclarationService.removeCustomDeclaration(id);
        return ResponseEntity.ok(ApiResponse.<CustomsDeclarationResponse>builder()
                .code(200)
                .message("Health Service Order Deleted")
                .result(result)
                .build());
    }

}
