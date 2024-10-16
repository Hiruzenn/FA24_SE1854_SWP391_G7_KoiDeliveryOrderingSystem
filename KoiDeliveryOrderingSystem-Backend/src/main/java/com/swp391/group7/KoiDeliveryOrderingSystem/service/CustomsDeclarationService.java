package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.CustomsDeclaration;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CustomsDeclarationDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.customsdeclaration.CreateCustomsDeclarationRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CustomsDeclarationRepository;
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
public class CustomsDeclarationService {
    @Autowired
    CustomsDeclarationRepository customsDeclarationRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<CustomsDeclarationDTO> getListCustomDeclaration(){
        List<CustomsDeclaration> customsDeclarationList= customsDeclarationRepository.findAll();
        if (customsDeclarationList.isEmpty()){
            throw new AppException(ErrorCode.CUSTOMSDECALARATION_NOT_EXISTED);
        }
        return customsDeclarationList.stream().map(customsDeclaration -> modelMapper.map(customsDeclaration, CustomsDeclarationDTO.class)).toList();
    }

    public CustomsDeclarationDTO getCustomsDeclaration(int id){
        CustomsDeclaration customsDeclaration= customsDeclarationRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.CUSTOMSDECALARATION_NOT_EXISTED));
        return modelMapper.map(customsDeclaration, CustomsDeclarationDTO.class);
    }

    public ApiResponse<CustomsDeclarationDTO> creatCustomsDeclaration(CreateCustomsDeclarationRequest createCustomsDeclarationRequest) {

        // Map the CreateCustomsDeclarationRequest to CustomsDeclaration entity
        CustomsDeclaration customsDeclaration = modelMapper.map(createCustomsDeclarationRequest, CustomsDeclaration.class);

        // Save the CustomsDeclaration entity to the repository
        customsDeclaration = customsDeclarationRepository.save(customsDeclaration); // Update the reference with the saved entity

        // Map the saved CustomsDeclaration entity to CustomsDeclarationDTO
        CustomsDeclarationDTO createdCustomsDeclarationDTO = modelMapper.map(customsDeclaration, CustomsDeclarationDTO.class);

        // Build and return the ApiResponse with the created CustomsDeclarationDTO
        return ApiResponse.<CustomsDeclarationDTO>builder()
                .code(HttpStatus.CREATED.value()) // HTTP status code for created (201)
                .message("Customs Declaration created successfully")
                .result(createdCustomsDeclarationDTO) // Use DTO here
                .build();
    }
    public ApiResponse<CustomsDeclarationDTO> updateCertificate(Integer id, CreateCustomsDeclarationRequest customsDeclarationRequest) {

        // Check if the customs declaration exists
        CustomsDeclaration customsDeclaration = customsDeclarationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMSDECALARATION_NOT_EXISTED));

        // Map the CreateCustomsDeclarationRequest to the existing CustomsDeclaration entity (update existing entity)
        modelMapper.map(customsDeclarationRequest, customsDeclaration);

        // Save the updated customs declaration to the repository
        customsDeclaration = customsDeclarationRepository.save(customsDeclaration); // Save and update entity

        // Map the updated CustomsDeclaration entity to CustomsDeclarationDTO
        CustomsDeclarationDTO customsDeclarationDTO = modelMapper.map(customsDeclaration, CustomsDeclarationDTO.class);

        // Build and return the ApiResponse with the updated CustomsDeclarationDTO
        return ApiResponse.<CustomsDeclarationDTO>builder()
                .code(HttpStatus.OK.value()) // HTTP status code for success (200)
                .message("Customs Declaration updated successfully")
                .result(customsDeclarationDTO) // Use the DTO here, not the request
                .build();
    }

    public ApiResponse<Void> deleteCertificate(Integer id) {
        // Check if the certificate exists
        if (!customsDeclarationRepository.existsById(id)) {
            throw new AppException(ErrorCode.CERTIFICATE_NOT_FOUND);
        }

        // Delete the certificate from the repository
        customsDeclarationRepository.deleteById(id);

        // Build and return the ApiResponse indicating success
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value()) // HTTP status code for no content (successful deletion)
                .message("CustomsDeclaration deleted successfully")
                .result(null) // No result for deletion
                .build();
    }
}
