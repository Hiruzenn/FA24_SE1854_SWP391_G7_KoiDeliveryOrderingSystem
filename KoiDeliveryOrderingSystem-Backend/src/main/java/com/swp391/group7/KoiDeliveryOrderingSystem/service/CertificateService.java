package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate.CreateCertificateRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Certificate;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CertificateResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CertificateRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
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
    private OrderRepository orderRepository;

    @Autowired
    private AccountUtils accountUtils;

    public List<CertificateResponse> getListCertificate() {
        List<Certificate> certificateList = certificateRepository.findCertificateByStatus(SystemStatusEnum.AVAILABLE);
        if (certificateList.isEmpty()) {
            throw new AppException(ErrorCode.CERTIFICATE_NOT_FOUND);
        }
        return convertToListCertificateResponse(certificateList);
    }

    public CertificateResponse getCertificateById(int id) {
        Certificate certificate = certificateRepository.findCertificateByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));
        return covertToCertificateResponse(certificate);

    }
    public List<CertificateResponse> getCertificateByOrder(Integer orderId){
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(()-> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<Certificate> certificate = certificateRepository.findByOrdersAndStatus(orders, SystemStatusEnum.AVAILABLE);
        return convertToListCertificateResponse(certificate);
    }

    public CertificateResponse creatCertificate(Integer orderId, CreateCertificateRequest certificateRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.PENDING).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Certificate certificate = Certificate.builder()
                .certificateName(certificateRequest.getCertificateName())
                .certificateDescription(certificateRequest.getCertificateDescription())
                .orders(orders)
                .health(certificateRequest.getHealth())
                .origin(certificateRequest.getOrigin())
                .award(certificateRequest.getAward())
                .image(certificateRequest.getImage())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        certificateRepository.save(certificate);
        return covertToCertificateResponse(certificate);
    }

    public CertificateResponse updateCertificate(Integer id, CreateCertificateRequest certificateRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Certificate certificate = certificateRepository.findCertificateByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));

        certificate.setCertificateName(certificateRequest.getCertificateName());
        certificate.setCertificateDescription(certificateRequest.getCertificateDescription());
        certificate.setImage(certificateRequest.getImage());
        certificate.setOrigin(certificateRequest.getOrigin());
        certificate.setAward(certificateRequest.getAward());
        certificate.setHealth(certificateRequest.getHealth());
        certificate.setUpdateAt(LocalDateTime.now());
        certificate.setUpdateBy(users.getId());

        certificate = certificateRepository.save(certificate);

        return covertToCertificateResponse(certificate);

    }

    public CertificateResponse deleteCertificate(Integer id) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));

        certificate.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        certificate.setUpdateAt(LocalDateTime.now());
        certificate.setUpdateBy(users.getId());
        certificateRepository.save(certificate);
        return covertToCertificateResponse(certificate);

    }


    public List<CertificateResponse> convertToListCertificateResponse(List<Certificate> certificateList) {
        List<CertificateResponse> certificateResponse = new ArrayList<>();
        for (Certificate certificate : certificateList) {
            certificateResponse.add(covertToCertificateResponse(certificate));
        }
        return certificateResponse;
    }

    public CertificateResponse covertToCertificateResponse(Certificate certificate) {
        return CertificateResponse.builder()
                .id(certificate.getId())
                .certificateName(certificate.getCertificateName())
                .certificateDescription(certificate.getCertificateDescription())
                .orderId(certificate.getOrders().getId())
                .health(certificate.getHealth())
                .origin(certificate.getOrigin())
                .award(certificate.getAward())
                .image(certificate.getImage())
                .createAt(certificate.getCreateAt())
                .createBy(certificate.getCreateBy())
                .updateAt(certificate.getUpdateAt())
                .updateBy(certificate.getUpdateBy())
                .status(certificate.getStatus())
                .build();
    }
}

