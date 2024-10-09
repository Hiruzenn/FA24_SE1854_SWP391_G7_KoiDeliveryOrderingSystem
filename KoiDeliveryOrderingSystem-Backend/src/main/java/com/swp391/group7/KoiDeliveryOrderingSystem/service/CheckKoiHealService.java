package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CheckingKoiHealthRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckKoiHealService {
    @Autowired
    CheckingKoiHealthRepository checkingKoiHealthRepository;
    @Autowired
    ModelMapper modelMapper;

//    public List<CheckKoiHealthResponse> getListCheckHealKoi()
//    {
//
//        List <CheckingKoiHealth> checkingKoiHealthList= checkingKoiHealthRepository.findAll();
//        if (checkingKoiHealthList.isEmpty()){
//            throw new AppException(ErrorCode.CERTIFICATE_NOT_FOUND);
//        }
//        return checkingKoiHealthList.stream().map(certificate -> modelMapper.map(ce, CertificateDTO.class)).toList();
//    }
}
