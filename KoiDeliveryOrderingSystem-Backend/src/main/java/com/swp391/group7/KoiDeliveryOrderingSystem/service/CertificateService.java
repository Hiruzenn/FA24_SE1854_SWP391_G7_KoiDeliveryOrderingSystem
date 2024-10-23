package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Feedback;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate.CreateCertificateRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CertificateDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Certificate;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CertificateRespone;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FeedbackResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CertificateRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CertificateService {
    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderRepository orderRepository;

    public List<CertificateRespone> getListCertificate()
    {

        List <Certificate> certificateList= certificateRepository.findAll();
        if (certificateList.isEmpty()){
            throw new AppException(ErrorCode.CERTIFICATE_NOT_FOUND);
        }
        return convertToListCertificateResponse(certificateList);
    }

    public CertificateRespone getCertificateById(int id){
        Certificate certificate= certificateRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));
        return covertToCertificateRespone(certificate);

    }

    public CertificateRespone creatCertificate(CreateCertificateRequest certificateRequest,Integer orderId) {

        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        // Map the CreateCertificateRequest to Certificate entity


        Certificate certificate = Certificate.builder()
                .certificateName(certificateRequest.getCertificateName())
                .certificateDescription(certificateRequest.getCertificateDescription())
                .orders(orders)
                .health(certificateRequest.getHealth())
                .origin(certificateRequest.getOrigin())
                .award(certificateRequest.getAward())
                .image(certificateRequest.getImage())
                .status(SystemStatusEnum.AVAILABLE)
                .build();



        certificateRepository.save(certificate); // Update the reference with the saved entity


        // Build and return the ApiResponse with the created CertificateDTO
        return covertToCertificateRespone(certificate);
    }

    public CertificateRespone updateCertificate(Integer id, CreateCertificateRequest certificateRequest) {
        // Check if the certificate exists
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));

        // Map the CreateCertificateRequest to the existing Certificate entity

        certificate.setCertificateName(certificateRequest.getCertificateName());
        certificate.setCertificateDescription(certificateRequest.getCertificateDescription());
        certificate.setImage(certificateRequest.getImage());
        certificate.setOrigin(certificateRequest.getOrigin());
        certificate.setAward(certificateRequest.getAward());
        certificate.setHealth(certificateRequest.getHealth());
        // Save the updated certificate to the repository
        certificate = certificateRepository.save(certificate); // Update the reference with the saved entity

        return covertToCertificateRespone(certificate);

    }
    public CertificateRespone  deleteCertificate(Integer id) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));

        certificate.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        certificateRepository.save(certificate);
        return covertToCertificateRespone(certificate);

    }


    public List<CertificateRespone> convertToListCertificateResponse(List<Certificate> certificateList) {
        List<CertificateRespone> certificateRespones = new ArrayList<>();
        for (Certificate certificate : certificateList) {
            certificateRespones.add(covertToCertificateRespone(certificate));
        }
        return certificateRespones;
    }

    public CertificateRespone covertToCertificateRespone(Certificate certificate){
        return CertificateRespone.builder()
                .id(certificate.getId())
                .certificateName(certificate.getCertificateName())
                .certificateDescription(certificate.getCertificateDescription())
                .orderId(certificate.getOrders().getId())
                .health(certificate.getHealth())
                .origin(certificate.getOrigin())
                .award(certificate.getAward())
                .image(certificate.getImage())
                .build();

    }


}

