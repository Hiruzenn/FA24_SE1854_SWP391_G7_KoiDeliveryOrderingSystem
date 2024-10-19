package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate.CreateCertificateRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CertificateRespone;
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
    public ResponseEntity<ApiResponse<List<CertificateRespone>>> getAllCertificates() {
        List<CertificateRespone> certificates = certificateService.getListCertificate();

        // Build and return the ApiResponse
        ApiResponse<List<CertificateRespone>> response = ApiResponse.<List<CertificateRespone>>builder()
                .code(200) // You can adjust the code based on your requirements
                .message("Certificates retrieved successfully") // Updated message for clarity
                .result(certificates)
                .build();

        return ResponseEntity.ok(response); // Return the response with HTTP 200 OK
    }

    @GetMapping("/{id}") // Mapping for GET request to retrieve a certificate by ID
    public ResponseEntity<ApiResponse<CertificateRespone>> getCertificateId(@PathVariable int id) {
        // Retrieve the certificate by ID using the service
        CertificateRespone CertificateRespone = certificateService.getCertificateById(id);

        // Check if the certificate was found
        if (CertificateRespone != null) {
            // Build and return the ApiResponse with the found certificate
            ApiResponse<CertificateRespone> response = ApiResponse.<CertificateRespone>builder()
                    .code(200)
                    .message("Certificate retrieved successfully")
                    .result(CertificateRespone)
                    .build();
            return ResponseEntity.ok(response); // Return HTTP 200 OK
        } else {
            // Return a not found response if the certificate does not exist
            ApiResponse<CertificateRespone> response = ApiResponse.<CertificateRespone>builder()
                    .code(404)
                    .message("Certificate not found")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return HTTP 404 Not Found
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CertificateRespone>> createCertificate(
            @RequestBody CreateCertificateRequest certificateRequest,
            @RequestParam Integer orderId) {
        try {
            CertificateRespone createdCertificateResponse = certificateService.creatCertificate(certificateRequest, orderId);
            ApiResponse<CertificateRespone> response = ApiResponse.<CertificateRespone>builder()
                    .code(200)
                    .message("Fish profile created successfully")
                    .result(createdCertificateResponse)
                    .build();
            return ResponseEntity.ok(response);
        } catch (AppException e) {
            ApiResponse<CertificateRespone> response = ApiResponse.<CertificateRespone>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<CertificateRespone> response = ApiResponse.<CertificateRespone>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PutMapping("update/{id}") // Endpoint to update a certificate by ID
    public ResponseEntity<ApiResponse<CertificateRespone>> updateCertificate(
            @PathVariable Integer id,
            @RequestBody CreateCertificateRequest certificateRequest) {
        try {
            // Call the service method to update the certificate
            CertificateRespone updatedCertificate = certificateService.updateCertificate(id, certificateRequest); // Update this line

            ApiResponse<CertificateRespone> response = ApiResponse.<CertificateRespone>builder()
                    .code(200)
                    .message("Fish profile created successfully")
                    .result(updatedCertificate)
                    .build();
            return ResponseEntity.ok(response);

        } catch (AppException e) {
            // Handle specific application exceptions
            ApiResponse<CertificateRespone> response = ApiResponse.<CertificateRespone>builder()
                    .code(e.getErrorCode().getCode()) // Set the specific error code
                    .message(e.getMessage()) // Set the error message from exception
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle general exceptions
            ApiResponse<CertificateRespone> response = ApiResponse.<CertificateRespone>builder()
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

