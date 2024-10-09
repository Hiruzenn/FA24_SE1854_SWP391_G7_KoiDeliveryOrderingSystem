package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.CreateCertificateRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CertificateDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Certificate;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CertificateRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CertificateService {
    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private ModelMapper modelMapper;



    public List<CertificateDTO> getListCertificate()
    {

        List <Certificate> certificateList= certificateRepository.findAll();
        if (certificateList.isEmpty()){
            throw new AppException(ErrorCode.CERTIFICATE_NOT_FOUND);
        }
        return certificateList.stream().map(certificate -> modelMapper.map(certificate, CertificateDTO.class)).toList();
    }

    public CertificateDTO getCertificateById(int id){
        Certificate certificate= certificateRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));
        return modelMapper.map(certificate, CertificateDTO.class);

    }

    public ApiResponse<CertificateDTO> creatCertificate(CreateCertificateRequest certificateRequest) {


        // Map the CreateCertificateRequest to Certificate entity
        Certificate certificate = modelMapper.map(certificateRequest, Certificate.class);

        // Save the certificate to the repository
        certificate = certificateRepository.save(certificate); // Update the reference with the saved entity

        // Map the saved Certificate back to CertificateDTO
        CertificateDTO createdCertificateDTO = modelMapper.map(certificate, CertificateDTO.class);

        // Build and return the ApiResponse with the created CertificateDTO
        return ApiResponse.<CertificateDTO>builder()
                .code(HttpStatus.CREATED.value()) // HTTP status code for created
                .message("Certificate created successfully")
                .result(createdCertificateDTO)
                .build();
    }

    public ApiResponse<CertificateDTO> updateCertificate(Integer id, CreateCertificateRequest certificateRequest) {
        // Check if the certificate exists
        Certificate existingCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));

        // Map the CreateCertificateRequest to the existing Certificate entity
        modelMapper.map(certificateRequest, existingCertificate); // Update the existing entity with new values

        // Save the updated certificate to the repository
        existingCertificate = certificateRepository.save(existingCertificate); // Update the reference with the saved entity

        // Map the saved Certificate back to CertificateDTO
        CertificateDTO updatedCertificateDTO = modelMapper.map(existingCertificate, CertificateDTO.class);

        // Build and return the ApiResponse with the updated CertificateDTO
        return ApiResponse.<CertificateDTO>builder()
                .code(HttpStatus.OK.value()) // HTTP status code for success
                .message("Certificate updated successfully")
                .result(updatedCertificateDTO)
                .build();
    }
    public ApiResponse<Void> deleteCertificate(Integer id) {
        // Check if the certificate exists
        if (!certificateRepository.existsById(id)) {
            throw new AppException(ErrorCode.CERTIFICATE_NOT_FOUND);
        }

        // Delete the certificate from the repository
        certificateRepository.deleteById(id);

        // Build and return the ApiResponse indicating success
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value()) // HTTP status code for no content (successful deletion)
                .message("Certificate deleted successfully")
                .result(null) // No result for deletion
                .build();
    }


}

