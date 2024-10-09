package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.dto.response.CertificateDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/certificates") // Base URL for the certificate endpoints
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping // GET request to retrieve the list of certificates
    public ResponseEntity<List<CertificateDTO>> getAllCertificates() {
        List<CertificateDTO> certificates = certificateService.getListCertificate();
        return new ResponseEntity<>(certificates, HttpStatus.OK); // Return 200 OK with the list of certificates
    }
}
