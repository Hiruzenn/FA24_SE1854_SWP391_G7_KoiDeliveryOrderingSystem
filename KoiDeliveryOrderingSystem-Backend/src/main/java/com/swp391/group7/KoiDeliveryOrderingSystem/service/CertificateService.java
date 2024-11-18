package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate.CreateCertificateRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Certificate;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.certificate.UpdateCertificateRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CertificateResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CertificateRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishProfileRepository;
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
    private AccountUtils accountUtils;
    @Autowired
    private FishProfileRepository fishProfileRepository;

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

    public List<CertificateResponse> viewCertificateByFishProfile(Integer fishProfileId) {
        FishProfile fishProfile = fishProfileRepository.findByIdAndStatus(fishProfileId, SystemStatusEnum.AVAILABLE).orElseThrow(() -> new AppException(ErrorCode.FISH_PROFILE_NOT_FOUND));
        List<Certificate> certificate = certificateRepository.findByFishProfileAndStatus(fishProfile, SystemStatusEnum.AVAILABLE);
        return convertToListCertificateResponse(certificate);
    }

    public CertificateResponse creatCertificate(Integer fishProfileId, CreateCertificateRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        FishProfile fishProfile = fishProfileRepository.findByIdAndStatus(fishProfileId, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_PROFILE_NOT_FOUND));
        Certificate certificate = Certificate.builder()
                .fishProfile(fishProfile)
                .name(request.getName())
                .species(request.getSpecies())
                .award(request.getAward())
                .sex(request.getSex())
                .size(request.getSize())
                .age(request.getAge())
                .image(request.getImage())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        certificateRepository.save(certificate);
        return covertToCertificateResponse(certificate);
    }

    public CertificateResponse updateCertificate(Integer id, UpdateCertificateRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Certificate certificate = certificateRepository.findCertificateByIdAndStatus(id, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));

        certificate.setName(request.getName());
        certificate.setSpecies(request.getSpecies());
        certificate.setAward(request.getAward());
        certificate.setSex(request.getSex());
        certificate.setSize(request.getSize());
        certificate.setAge(request.getAge());
        certificate.setImage(request.getImage());
        certificate.setAward(request.getAward());
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
                .fishProleId(certificate.getFishProfile().getId())
                .name(certificate.getName())
                .award(certificate.getAward())
                .species(certificate.getSpecies())
                .sex(certificate.getSex())
                .size(certificate.getSize())
                .age(certificate.getAge())
                .image(certificate.getImage())
                .createAt(certificate.getCreateAt())
                .createBy(certificate.getCreateBy())
                .updateAt(certificate.getUpdateAt())
                .updateBy(certificate.getUpdateBy())
                .status(certificate.getStatus())
                .build();
    }
}

