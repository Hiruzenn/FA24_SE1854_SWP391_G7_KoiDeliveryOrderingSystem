package com.swp391.group7.KoiDeliveryOrderingSystem.service;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.CreateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.HandoverDocumentDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HandoverDocument;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HandoverDocumentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HandoverDocumentRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HealthCareDeliveryHistoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.PackageRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.LuhnCheck;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HandoverDocumentService {

    @Autowired
    private HandoverDocumentRepository handoverDocumentRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    HealthCareDeliveryHistoryRepository healthCareDeliveryHistoryRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountUtils accountUtils;

    // Retrieve the list of all handover documents
    public List<HandoverDocumentDTO> getListHandoverDocuments() {
        List<HandoverDocument> handoverDocumentList = handoverDocumentRepository.findAll();
        if (handoverDocumentList.isEmpty()) {
            throw new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND);
        }
        return handoverDocumentList.stream()
                .map(handoverDocument -> modelMapper.map(handoverDocument, HandoverDocumentDTO.class))
                .toList();
    }

    // Retrieve a specific handover document by its ID
    public HandoverDocumentResponse getHandoverDocumentById(int id) {
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        return covertToHandoverDocumentResponse(handoverDocument);
    }

    // Create a new handover document
    public HandoverDocumentResponse createHandoverDocument(
            CreateHandoverDocumentRequest handoverDocumentRequest,
            int orderId,
            int packageId) {
        Users users = accountUtils.getCurrentUser();
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (users == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        Package packages= packageRepository.findById(packageId).orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_EXISTED));
        HandoverDocument handoverDocument = HandoverDocument.builder()
                .handoverNo(handoverDocumentRequest.getHandoverNo())  // Example: using a value from handoverDocumentRequest
                .users(users)                                      // Set the Users object
                .packages(packages)                                // Set the Package object
                .orders(orders)                                    // Set the Orders object
                .staff(handoverDocumentRequest.getStaff())             // Set the staff field
                .handoverDescription(handoverDocumentRequest.getHandoverDescription()) // Set the description
                .vehicle(handoverDocumentRequest.getVehicle())         // Set the vehicle field
                .destination(handoverDocumentRequest.getDestination()) // Set the destination
                .departure(handoverDocumentRequest.getDeparture())     // Set the departure field
                .totalPrice(handoverDocumentRequest.getTotalPrice())   // Set the total price
                .createAt(LocalDateTime.now())                      // Set the creation time
                .createBy(users.getId())                            // Set the ID of the user who created it
                .status(SystemStatusEnum.AVAILABLE)                    // Set the status
                .build();

        handoverDocument = handoverDocumentRepository.save(handoverDocument);

        // Map the saved document back to HandoverDocumentDTO
        HandoverDocumentDTO createdHandoverDocumentDTO = modelMapper.map(handoverDocument, HandoverDocumentDTO.class);

        // Build and return the ApiResponse with the created document
        return covertToHandoverDocumentResponse(handoverDocument);
    }

    // Update an existing handover document by ID
    public HandoverDocumentResponse updateHandoverDocument(Integer id, CreateHandoverDocumentRequest handoverDocumentRequest) {
        // Fetch the existing HandoverDocument by ID
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));

        // Update the fields with the request data
        handoverDocument.setHandoverNo(handoverDocumentRequest.getHandoverNo());
        handoverDocument.setUsers(handoverDocumentRequest.getUsers());
        handoverDocument.setPackages(handoverDocumentRequest.getPackages());
        handoverDocument.setOrders(handoverDocumentRequest.getOrders());
        handoverDocument.setHealthCareDeliveryHistory(handoverDocumentRequest.getHealthCareDeliveryHistory());
        handoverDocument.setStaff(handoverDocumentRequest.getStaff());
        handoverDocument.setHandoverDescription(handoverDocumentRequest.getHandoverDescription());
        handoverDocument.setVehicle(handoverDocumentRequest.getVehicle());
        handoverDocument.setDestination(handoverDocumentRequest.getDestination());
        handoverDocument.setDeparture(handoverDocumentRequest.getDeparture());
        handoverDocument.setTotalPrice(handoverDocumentRequest.getTotalPrice());
        handoverDocument.setUpdateAt(LocalDateTime.now()); // Update the timestamp
        handoverDocument.setUpdateBy(accountUtils.getCurrentUser().getId()); // Set the updater's ID

        // Save the updated HandoverDocument
        handoverDocument = handoverDocumentRepository.save(handoverDocument);

        // Map the updated HandoverDocument to HandoverDocumentResponse
        HandoverDocumentResponse response = covertToHandoverDocumentResponse(handoverDocument);

        // Return the response
        return response;
    }

    public HandoverDocumentResponse removeHandoverDocument(Integer id) {
        // Fetch the existing HandoverDocument by ID
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));

        handoverDocument.setStatus(SystemStatusEnum.NOT_AVAILABLE);

        // Save the updated HandoverDocument
        handoverDocument = handoverDocumentRepository.save(handoverDocument);

        // Map the updated HandoverDocument to HandoverDocumentResponse
        HandoverDocumentResponse response = covertToHandoverDocumentResponse(handoverDocument);

        // Return the response
        return response;
    }

    public HandoverDocumentResponse covertToHandoverDocumentResponse(HandoverDocument handoverDocument){
        return HandoverDocumentResponse.builder()
                .id(handoverDocument.getId())
                .users(handoverDocument.getUsers())
                .packages(handoverDocument.getPackages())
                .orders(handoverDocument.getOrders())
                .healthCareDeliveryHistory(handoverDocument.getHealthCareDeliveryHistory())
                .handoverNo(handoverDocument.getHandoverNo())
                .staff(handoverDocument.getStaff())
                .handoverDescription(handoverDocument.getHandoverDescription())
                .vehicle(handoverDocument.getVehicle())
                .destination(handoverDocument.getDestination())
                .departure(handoverDocument.getDeparture())
                .totalPrice(handoverDocument.getTotalPrice())
                .createAt(handoverDocument.getCreateAt())
                .createBy(handoverDocument.getCreateBy())
                .updateAt(handoverDocument.getUpdateAt())
                .updateBy(handoverDocument.getUpdateBy())
                .status(handoverDocument.getStatus())
                .build();
    }
}
