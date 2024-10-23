package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate.CreateCertificateRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.customsdeclaration.CreateCustomsDeclarationRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CertificateRespone;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CustomsDeclarationRespone;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthServiceOrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.CustomsDeclarationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customsdecalaration")
public class CustomsDecalarationController {
    @Autowired
    CustomsDeclarationService customsDeclarationService;


    @GetMapping // GET request to retrieve the list of customs declarations
    public ResponseEntity<ApiResponse<List<CustomsDeclarationRespone>>> getAllCustomDeclarations() {
        List<CustomsDeclarationRespone> customsDeclarations = customsDeclarationService.getListCustomDeclaration();

        // Build and return the ApiResponse
        ApiResponse<List<CustomsDeclarationRespone>> response = ApiResponse.<List<CustomsDeclarationRespone>>builder()
                .code(HttpStatus.OK.value()) // HTTP 200 OK
                .message("Customs declarations retrieved successfully")
                .result(customsDeclarations)
                .build();

        return ResponseEntity.ok(response); // Return the response with HTTP 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomsDeclarationRespone>> getCustomDeclarationById(@PathVariable Integer id) {
        try {
            // Retrieve the customs declaration by ID
            CustomsDeclarationRespone CustomsDeclarationRespone = customsDeclarationService.getCustomsDeclaration(id);

            // Build and return the ApiResponse with the found customs declaration
            ApiResponse<CustomsDeclarationRespone> response = ApiResponse.<CustomsDeclarationRespone>builder()
                    .code(HttpStatus.OK.value()) // HTTP 200 OK
                    .message("Customs declaration retrieved successfully")
                    .result(CustomsDeclarationRespone)
                    .build();

            return ResponseEntity.ok(response); // Return HTTP 200 OK
        } catch (AppException e) {
            // Handle exception and build error response
            ApiResponse<CustomsDeclarationRespone> response = ApiResponse.<CustomsDeclarationRespone>builder()
                    .code(e.getErrorCode().getCode()) // Error code from exception
                    .message(e.getMessage()) // Error message from exception
                    .result(null)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return HTTP 404 Not Found
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CustomsDeclarationRespone>> createCertificate(
            @RequestBody CreateCustomsDeclarationRequest customsDeclarationRequest,
            @RequestParam Integer orderId) {
        try {
            CustomsDeclarationRespone customsDeclarationRespone = customsDeclarationService.creatCustomsDeclaration(customsDeclarationRequest, orderId);
            ApiResponse<CustomsDeclarationRespone> response = ApiResponse.<CustomsDeclarationRespone>builder()
                    .code(200)
                    .message("Customs declaration created successfully")
                    .result(customsDeclarationRespone)
                    .build();
            return ResponseEntity.ok(response);
        } catch (AppException e) {
            ApiResponse<CustomsDeclarationRespone> response = ApiResponse.<CustomsDeclarationRespone>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<CustomsDeclarationRespone> response = ApiResponse.<CustomsDeclarationRespone>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PutMapping("/update/{id}") // Endpoint to update a customs declaration by ID
    public ResponseEntity<ApiResponse<CustomsDeclarationRespone>> updateCustomDeclaration(
            @PathVariable Integer id,
            @RequestBody CreateCustomsDeclarationRequest request) {
        try {
            // Call service to update the customs declaration
            CustomsDeclarationRespone updatedCustomsDeclarationRespone = customsDeclarationService.updateCustomsDeclaration(id, request);

            ApiResponse<CustomsDeclarationRespone> response = ApiResponse.<CustomsDeclarationRespone>builder()
                    .code(200)
                    .message("Customs declaration updated successfully")
                    .result(updatedCustomsDeclarationRespone)
                    .build();
            return ResponseEntity.ok(response);
        } catch (AppException e) {
            // Handle specific application exceptions
            ApiResponse<CustomsDeclarationRespone> response = ApiResponse.<CustomsDeclarationRespone>builder()
                    .code(e.getErrorCode().getCode()) // Error code from exception
                    .message(e.getMessage()) // Error message from exception
                    .result(null)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle general exceptions
            ApiResponse<CustomsDeclarationRespone> response = ApiResponse.<CustomsDeclarationRespone>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value()) // HTTP 500
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }

    @PutMapping("/delete/{id}") // Endpoint to delete a customs declaration by ID
    public ApiResponse<CustomsDeclarationRespone> deleteCustomDeclaration(@PathVariable Integer id) {
        customsDeclarationService.removeCustomDeclaration(id);
        return ApiResponse.<CustomsDeclarationRespone>builder()
                .code(200)
                .message("Health Service Order Deleted")
                .build();
    }


}
