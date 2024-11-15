package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate.CreateCertificateRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate.UpdateCertificateRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CertificateResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.CertificateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("certificates")
@Tag(name = "Certificate")
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

    @GetMapping("view-by-fish-profile/{fishProfileId}")
    public ResponseEntity<ApiResponse<List<CertificateResponse>>> getCertificateByFishProfileId(@PathVariable int fishProfileId) {
        var result = certificateService.viewCertificateByFishProfile(fishProfileId);
        return ResponseEntity.ok(ApiResponse.<List<CertificateResponse>>builder()
                .code(200)
                .message("Certificate by Fish Profile")
                .result(result)
                .build());
    }
    @PostMapping("/create/{fishProfileId}")
    public ResponseEntity<ApiResponse<CertificateResponse>> createCertificate(@Valid
            @RequestBody CreateCertificateRequest certificateRequest,
            @PathVariable Integer fishProfileId) {

        var result = certificateService.creatCertificate(fishProfileId, certificateRequest);
        return ResponseEntity.ok(ApiResponse.<CertificateResponse>builder()
                .code(200)
                .message("Certificate created successfully")
                .result(result)
                .build());
    }

    @PutMapping("update/{id}") // Endpoint to update a certificate by ID
    public ResponseEntity<ApiResponse<CertificateResponse>> updateCertificate(@Valid
            @PathVariable Integer id,
            @RequestBody UpdateCertificateRequest request) {
        var result = certificateService.updateCertificate(id, request);
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

