package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate.CreateCertificateRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CertificateDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/certificates") // Base URL for the certificate endpoints
public class CertificateController {

    @Autowired
    private CertificateService certificateService;


    @GetMapping // GET request to retrieve the list of certificates
    public ResponseEntity<ApiResponse<List<CertificateDTO>>> getAllCertificates() {
        List<CertificateDTO> certificates = certificateService.getListCertificate();

        // Build and return the ApiResponse
        ApiResponse<List<CertificateDTO>> response = ApiResponse.<List<CertificateDTO>>builder()
                .code(200) // You can adjust the code based on your requirements
                .message("Certificates retrieved successfully") // Updated message for clarity
                .result(certificates)
                .build();

        return ResponseEntity.ok(response); // Return the response with HTTP 200 OK
    }

    @GetMapping("/{id}") // Mapping for GET request to retrieve a certificate by ID
    public ResponseEntity<ApiResponse<CertificateDTO>> getCertificateId(@PathVariable int id) {
        // Retrieve the certificate by ID using the service
        CertificateDTO certificateDTO = certificateService.getCertificateById(id);

        // Check if the certificate was found
        if (certificateDTO != null) {
            // Build and return the ApiResponse with the found certificate
            ApiResponse<CertificateDTO> response = ApiResponse.<CertificateDTO>builder()
                    .code(200)
                    .message("Certificate retrieved successfully")
                    .result(certificateDTO)
                    .build();
            return ResponseEntity.ok(response); // Return HTTP 200 OK
        } else {
            // Return a not found response if the certificate does not exist
            ApiResponse<CertificateDTO> response = ApiResponse.<CertificateDTO>builder()
                    .code(404)
                    .message("Certificate not found")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return HTTP 404 Not Found
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CertificateDTO>> createCertificate(@RequestBody CreateCertificateRequest certificateRequest) {
        try {
            ApiResponse<CertificateDTO> createdCertificateResponse = certificateService.creatCertificate(certificateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCertificateResponse);
        } catch (AppException e) {
            ApiResponse<CertificateDTO> response = ApiResponse.<CertificateDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<CertificateDTO> response = ApiResponse.<CertificateDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PutMapping("update/{id}") // Endpoint to update a certificate by ID
    public ResponseEntity<ApiResponse<CertificateDTO>> updateCertificate(
            @PathVariable Integer id,
            @RequestBody CreateCertificateRequest certificateRequest) {
        try {
            // Call the service method to update the certificate
            ApiResponse<CertificateDTO> updatedCertificate = certificateService.updateCertificate(id, certificateRequest); // Update this line

            return ResponseEntity.status(HttpStatus.OK).body(updatedCertificate);

        } catch (AppException e) {
            // Handle specific application exceptions
            ApiResponse<CertificateDTO> response = ApiResponse.<CertificateDTO>builder()
                    .code(e.getErrorCode().getCode()) // Set the specific error code
                    .message(e.getMessage()) // Set the error message from exception
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle general exceptions
            ApiResponse<CertificateDTO> response = ApiResponse.<CertificateDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCertificate(@PathVariable Integer id) {
        try {
            ApiResponse<Void> response = certificateService.deleteCertificate(id);
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

