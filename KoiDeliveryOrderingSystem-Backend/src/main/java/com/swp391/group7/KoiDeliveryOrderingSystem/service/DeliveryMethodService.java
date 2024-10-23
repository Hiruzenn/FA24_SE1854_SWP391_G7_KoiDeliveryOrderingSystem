package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.DeliveryMethod;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod.CreateDeliveryMethodRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod.UpdateDeliveryMethodRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.DeliveryMethodResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.DeliveryMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor

public class DeliveryMethodService {
    @Autowired
    private final DeliveryMethodRepository deliveryMethodRepository;


    public DeliveryMethodResponse createDeliveryMethod(CreateDeliveryMethodRequest createDeliveryMethodRequest) {
        DeliveryMethod deliveryMethod = DeliveryMethod.builder()
                .deliveryName(createDeliveryMethodRequest.getDelivery_name())
                .build();
        deliveryMethodRepository.save(deliveryMethod);
        return convertToDeliveryMethodResponse(deliveryMethod);
    }

    public DeliveryMethodResponse updateDeliveryMethod(Integer id, UpdateDeliveryMethodRequest updateDeliveryMethodRequest){
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND));

        deliveryMethod.setDeliveryName(updateDeliveryMethodRequest.getDelivery_name());
        deliveryMethodRepository.save(deliveryMethod);

        return convertToDeliveryMethodResponse(deliveryMethod);


}

    public List<DeliveryMethodResponse> viewAllDeliveryMethods(){
        List<DeliveryMethod> deliveryMethods = deliveryMethodRepository.findAll();
        List<DeliveryMethodResponse> deliveryMethodResponses = new ArrayList<>();

        for (DeliveryMethod deliveryMethod : deliveryMethods) {
            DeliveryMethodResponse response = convertToDeliveryMethodResponse(deliveryMethod);
            deliveryMethodResponses.add(response);
        }

        return deliveryMethodResponses;
    }

    public DeliveryMethodResponse deleteDeliveryMethod(Integer id) {
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND));

        DeliveryMethodResponse response = convertToDeliveryMethodResponse(deliveryMethod);
        deliveryMethodRepository.deleteById(id);

        return response;
    }

    public DeliveryMethodResponse convertToDeliveryMethodResponse(DeliveryMethod deliveryMethod){
        return DeliveryMethodResponse.builder()
                .id(deliveryMethod.getId())
                .delivery_name(deliveryMethod.getDeliveryName())
                .build();
    }
/*
    // Method to retrieve all DeliveryMethods
    public List<DeliveryMethodDTO> getListDeliveryMethods() {
        List<DeliveryMethod> deliveryMethodList = deliveryMethodRepository.findAll();

        // Check if the list is empty and throw an exception if no DeliveryMethods are found
        if (deliveryMethodList.isEmpty()) {
            throw new AppException(ErrorCode.DELIVERYMETHOD_NOT_FOUND);
        }

        // Map the list of DeliveryMethod entities to DeliveryMethodDTOs
        return deliveryMethodList.stream()
                .map(DeliveryMethod -> modelMapper.map(DeliveryMethod, DeliveryMethodDTO.class))
                .toList();
    }

    // Method to retrieve an DeliveryMethod by ID
    public DeliveryMethodDTO getDeliveryMethodById(int id) {
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERYMETHOD_NOT_FOUND));

        // Map the DeliveryMethod entity to DeliveryMethodDTO
        return modelMapper.map(deliveryMethod, DeliveryMethodDTO.class);
    }

    // Method to create a new DeliveryMethod
    public ApiResponse<DeliveryMethodDTO> createDeliveryMethod(CreateDeliveryMethodRequest createDeliveryMethodRequest) {
        // Map the CreateDeliveryMethodRequest to the DeliveryMethod entity
        DeliveryMethod deliveryMethod = modelMapper.map(createDeliveryMethodRequest, DeliveryMethod.class);

        // Save the DeliveryMethod to the repository
         deliveryMethodRepository.save(deliveryMethod);

        // Map the saved DeliveryMethod entity to DeliveryMethodDTO
        DeliveryMethodDTO createdDeliveryMethodDTO = modelMapper.map(deliveryMethod, DeliveryMethodDTO.class);

        // Build and return the ApiResponse with the created DeliveryMethod
        return ApiResponse.<DeliveryMethodDTO>builder()
                .code(HttpStatus.CREATED.value()) // 201 Created
                .message("DeliveryMethod created successfully")
                .result(createdDeliveryMethodDTO)
                .build();
    }

    // Method to update an existing DeliveryMethod by ID
    public ApiResponse<DeliveryMethodDTO> updateDeliveryMethod(Integer id, CreateDeliveryMethodRequest createDeliveryMethodRequest) {
        // Find the DeliveryMethod by ID, throw exception if not found
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERYMETHOD_NOT_FOUND));

        // Map the update data to the existing DeliveryMethod entity
        modelMapper.map(createDeliveryMethodRequest, deliveryMethod);

        // Save the updated DeliveryMethod
        deliveryMethod = deliveryMethodRepository.save(deliveryMethod);

        // Map the updated DeliveryMethod entity to DeliveryMethodDTO
        DeliveryMethodDTO updatedDeliveryMethodDTO = modelMapper.map(deliveryMethod, DeliveryMethodDTO.class);

        // Build and return the ApiResponse with the updated DeliveryMethod
        return ApiResponse.<DeliveryMethodDTO>builder()
                .code(HttpStatus.OK.value()) // 200 OK
                .message("DeliveryMethod updated successfully")
                .result(updatedDeliveryMethodDTO)
                .build();
    }

    // Method to delete an DeliveryMethod by ID
    public ApiResponse<Void> deleteDeliveryMethod(Integer id) {
        // Check if the DeliveryMethod exists
        if (!deliveryMethodRepository.existsById(id)) {
            throw new AppException(ErrorCode.DELIVERYMETHOD_NOT_FOUND);
        }

        // Delete the DeliveryMethod
        deliveryMethodRepository.deleteById(id);

        // Build and return the ApiResponse indicating success
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value()) // 204 No Content
                .message("DeliveryMethod deleted successfully")
                .result(null) // No result for deletion
                .build();
    }

 */
}

