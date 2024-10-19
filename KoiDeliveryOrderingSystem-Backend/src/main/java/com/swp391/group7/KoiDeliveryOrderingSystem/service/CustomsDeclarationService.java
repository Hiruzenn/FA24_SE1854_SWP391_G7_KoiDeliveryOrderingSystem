package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.CustomsDeclaration;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.customsdeclaration.CreateCustomsDeclarationRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CustomerRespone;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CustomsDeclarationRespone;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CustomsDeclarationRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomsDeclarationService {
    @Autowired
    CustomsDeclarationRepository customsDeclarationRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    OrderRepository orderRepository;

    public List<CustomsDeclarationRespone> getListCustomDeclaration(){
        List<CustomsDeclaration> customsDeclarationList= customsDeclarationRepository.findAll();
        if (customsDeclarationList.isEmpty()){
            throw new AppException(ErrorCode.CUSTOMSDECALARATION_NOT_EXISTED);
        }
        return convertToListCustomsDeclarationRespone(customsDeclarationList);
    }

    public CustomsDeclarationRespone getCustomsDeclaration(int id){
        CustomsDeclaration customsDeclaration= customsDeclarationRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.CUSTOMSDECALARATION_NOT_EXISTED));
        return covertToCustomsDeclarationRespone(customsDeclaration);
    }

    public CustomsDeclarationRespone creatCustomsDeclaration(CreateCustomsDeclarationRequest createCustomsDeclarationRequest, Integer orderId) {

        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        CustomsDeclaration customsDeclaration = CustomsDeclaration.builder()
                    .customsName(createCustomsDeclarationRequest.getCustomsName())
                    .declarationDate(createCustomsDeclarationRequest.getDeclarationDate())
                    .declarationNo(createCustomsDeclarationRequest.getDeclarationNo())
                    .declaratonBy(createCustomsDeclarationRequest.getDeclaratonBy())
                    .orders(orders)
                    .referenceeDate(createCustomsDeclarationRequest.getReferenceeDate())
                    .image(createCustomsDeclarationRequest.getImage())
                    .referenceNo(createCustomsDeclarationRequest.getReferenceNo())
                    .status(SystemStatusEnum.AVAILABLE)
                    .build();
        customsDeclarationRepository.save(customsDeclaration);
        return covertToCustomsDeclarationRespone(customsDeclaration);
    }
    public CustomsDeclarationRespone updateCustomsDeclaration(Integer id, CreateCustomsDeclarationRequest customsDeclarationRequest) {

        // Check if the customs declaration exists
        CustomsDeclaration customsDeclaration = customsDeclarationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMSDECALARATION_NOT_EXISTED));

       customsDeclaration.setDeclarationNo(customsDeclarationRequest.getDeclarationNo());
       customsDeclaration.setCustomsName(customsDeclarationRequest.getCustomsName());
       customsDeclaration.setDeclarationDate(customsDeclarationRequest.getDeclarationDate());
       customsDeclaration.setImage(customsDeclarationRequest.getImage());
       customsDeclaration.setDeclaratonBy(customsDeclarationRequest.getDeclaratonBy());
       customsDeclaration.setReferenceeDate(customsDeclarationRequest.getReferenceeDate());
       customsDeclaration.setReferenceNo(customsDeclarationRequest.getReferenceNo());


         customsDeclarationRepository.save(customsDeclaration); // Save and update entity

        return covertToCustomsDeclarationRespone(customsDeclaration);
    }


    public CustomsDeclarationRespone removeCustomDeclaration(int id){
        CustomsDeclaration customsDeclaration = customsDeclarationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMSDECALARATION_NOT_EXISTED));
        customsDeclaration.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        customsDeclarationRepository.save(customsDeclaration);
        return covertToCustomsDeclarationRespone(customsDeclaration);

    }


    public List<CustomsDeclarationRespone> convertToListCustomsDeclarationRespone(List<CustomsDeclaration> customsDeclarationList) {
        List<CustomsDeclarationRespone> customsDeclarationResponses = new ArrayList<>();
        for (CustomsDeclaration customsDeclaration : customsDeclarationList) {
            customsDeclarationResponses.add(covertToCustomsDeclarationRespone(customsDeclaration));
        }
        return customsDeclarationResponses;
    }

    public CustomsDeclarationRespone covertToCustomsDeclarationRespone(CustomsDeclaration customsDeclaration){
        return CustomsDeclarationRespone.builder()
                .declarationNo(customsDeclaration.getDeclarationNo())
                .customsName(customsDeclaration.getCustomsName())
                .referenceNo(customsDeclaration.getReferenceNo())
                .declarationDate(customsDeclaration.getDeclarationDate())
                .declaratonBy(customsDeclaration.getDeclaratonBy())
                .image(customsDeclaration.getImage())
                .orders(customsDeclaration.getOrders())
                .referenceeDate(customsDeclaration.getReferenceeDate())
                .id(customsDeclaration.getId())
                .stautus(customsDeclaration.getStatus())
                .build();
    }
}
