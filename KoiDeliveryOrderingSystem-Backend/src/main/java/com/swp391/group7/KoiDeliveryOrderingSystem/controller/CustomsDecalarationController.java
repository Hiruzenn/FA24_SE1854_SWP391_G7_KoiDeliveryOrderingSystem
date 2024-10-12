package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CustomsDeclarationDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.customsdeclaration.CreateCustomsDeclarationRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
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
    @Autowired
    ModelMapper modelMapper;

    @GetMapping // GET request to retrieve the list of customs declarations
    public ResponseEntity<ApiResponse<List<CustomsDeclarationDTO>>> getAllCustomDeclarations() {
        List<CustomsDeclarationDTO> customsDeclarations = customsDeclarationService.getListCustomDeclaration();

        // Build and return the ApiResponse
        ApiResponse<List<CustomsDeclarationDTO>> response = ApiResponse.<List<CustomsDeclarationDTO>>builder()
                .code(HttpStatus.OK.value()) // HTTP 200 OK
                .message("Customs declarations retrieved successfully")
                .result(customsDeclarations)
                .build();

        return ResponseEntity.ok(response); // Return the response with HTTP 200 OK
    }

    @GetMapping("/{id}") // Mapping for GET request to retrieve a customs declaration by ID
    public ResponseEntity<ApiResponse<CustomsDeclarationDTO>> getCustomDeclarationById(@PathVariable Integer id) {
        try {
            // Retrieve the customs declaration by ID
            CustomsDeclarationDTO customsDeclarationDTO = customsDeclarationService.getCustomsDeclaration(id);

            // Build and return the ApiResponse with the found customs declaration
            ApiResponse<CustomsDeclarationDTO> response = ApiResponse.<CustomsDeclarationDTO>builder()
                    .code(HttpStatus.OK.value()) // HTTP 200 OK
                    .message("Customs declaration retrieved successfully")
                    .result(customsDeclarationDTO)
                    .build();

            return ResponseEntity.ok(response); // Return HTTP 200 OK

        } catch (AppException e) {
            // Handle not found exception
            ApiResponse<CustomsDeclarationDTO> response = ApiResponse.<CustomsDeclarationDTO>builder()
                    .code(e.getErrorCode().getCode()) // Error code from exception
                    .message(e.getMessage()) // Error message from exception
                    .result(null)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return HTTP 404 Not Found
        }
    }

    @PostMapping("/create") // Endpoint to create a new customs declaration
    public ResponseEntity<ApiResponse<CustomsDeclarationDTO>> createCustomDeclaration(@RequestBody CreateCustomsDeclarationRequest request) {
        try {
            // Call service to create the customs declaration
            ApiResponse<CustomsDeclarationDTO> response = customsDeclarationService.creatCustomsDeclaration(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(response); // Return HTTP 201 Created
        } catch (AppException e) {
            // Handle specific application exceptions
            ApiResponse<CustomsDeclarationDTO> response = ApiResponse.<CustomsDeclarationDTO>builder()
                    .code(e.getErrorCode().getCode()) // Error code from exception
                    .message(e.getMessage()) // Error message from exception
                    .result(null)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle general exceptions
            ApiResponse<CustomsDeclarationDTO> response = ApiResponse.<CustomsDeclarationDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value()) // HTTP 500
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }

    @PutMapping("/update/{id}") // Endpoint to update a customs declaration by ID
    public ResponseEntity<ApiResponse<CustomsDeclarationDTO>> updateCustomDeclaration(
            @PathVariable Integer id,
            @RequestBody CreateCustomsDeclarationRequest request) {
        try {
            // Call service to update the customs declaration
            ApiResponse<CustomsDeclarationDTO> response = customsDeclarationService.updateCertificate(id, request);

            return ResponseEntity.status(HttpStatus.OK).body(response); // Return HTTP 200 OK
        } catch (AppException e) {
            // Handle specific application exceptions
            ApiResponse<CustomsDeclarationDTO> response = ApiResponse.<CustomsDeclarationDTO>builder()
                    .code(e.getErrorCode().getCode()) // Error code from exception
                    .message(e.getMessage()) // Error message from exception
                    .result(null)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle general exceptions
            ApiResponse<CustomsDeclarationDTO> response = ApiResponse.<CustomsDeclarationDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value()) // HTTP 500
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }

    @DeleteMapping("/delete/{id}") // Endpoint to delete a customs declaration by ID
    public ResponseEntity<ApiResponse<Void>> deleteCustomDeclaration(@PathVariable Integer id) {
        try {
            // Call service to delete the customs declaration
            ApiResponse<Void> response = customsDeclarationService.deleteCertificate(id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response); // Return HTTP 204 No Content
        } catch (AppException e) {
            // Handle specific application exceptions
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .code(e.getErrorCode().getCode()) // Error code from exception
                    .message(e.getMessage()) // Error message from exception
                    .result(null)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle general exceptions
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value()) // HTTP 500
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }


}
