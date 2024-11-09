package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate.CreateCertificateRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CertificateResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.CertificateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;


    @GetMapping ("view-all")
    public ResponseEntity<ApiResponse<List<CertificateResponse>>> getAllCertificates() {
        var result = certificateService.getListCertificate();
        return ResponseEntity.ok(ApiResponse.<List<CertificateResponse>>builder()
                .code(200)
                .message("All Certificates")
                .result(result)
                .build());
    }

    @GetMapping("view-one/{id}") // Mapping for GET request to retrieve a certificate by ID
    public ResponseEntity<ApiResponse<CertificateResponse>> getCertificateId(@PathVariable int id) {
        var result = certificateService.getCertificateById(id);
        return ResponseEntity.ok(ApiResponse.<CertificateResponse>builder()
                .code(200)
                .message("Certificate by id")
                .result(result)
                .build());

    }

    @GetMapping("view-by-order/{orderId}")
    public ResponseEntity<ApiResponse<List<CertificateResponse>>> getCertificateByOrderId(@PathVariable("orderId") int orderId) {
        var result = certificateService.getCertificateByOrder(orderId);
        return ResponseEntity.ok(ApiResponse.<List<CertificateResponse>>builder()
                .code(200)
                .message("Certificates by Orders")
                .result(result)
                .build());
    }


    @PostMapping("/create/{orderId}")
    public ResponseEntity<ApiResponse<CertificateResponse>> createCertificate(@Valid
            @RequestBody CreateCertificateRequest certificateRequest,
            @PathVariable Integer orderId) {

        var result = certificateService.creatCertificate(orderId, certificateRequest);
        return ResponseEntity.ok(ApiResponse.<CertificateResponse>builder()
                .code(200)
                .message("Certificate created successfully")
                .result(result)
                .build());
    }

    @PutMapping("update/{id}") // Endpoint to update a certificate by ID
    public ResponseEntity<ApiResponse<CertificateResponse>> updateCertificate(@Valid
            @PathVariable Integer id,
            @RequestBody CreateCertificateRequest certificateRequest) {
        var result = certificateService.updateCertificate(id, certificateRequest);
        return ResponseEntity.ok(ApiResponse.<CertificateResponse>builder()
                .code(200)
                .message("Certificate created successfully")
                .result(result)
                .build());
    }

    @PutMapping("delete/{id}")
    public ResponseEntity<ApiResponse<CertificateResponse>> deleteCertificate(@PathVariable Integer id) {
        var result = certificateService.deleteCertificate(id);
        return ResponseEntity.ok(ApiResponse.<CertificateResponse>builder()
                .code(200)
                .message("Certificate deleted successfully")
                .result(result)
                .build());

    }

}

