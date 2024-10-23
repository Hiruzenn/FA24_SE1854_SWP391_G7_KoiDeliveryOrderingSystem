package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.CreateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.UpdateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HandoverDocumentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HandoverDocument;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HandoverDocumentRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.PackageRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HandoverDocumentService {

    @Autowired
    private final HandoverDocumentRepository handoverDocumentRepository;

    @Autowired
    private AccountUtils accountUtils;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PackageRepository packageRepository;

    public static final String RANDOM_STRING = "0123456789";

    public HandoverDocumentResponse createHandoverDocument(Integer orderId, Integer packageId, CreateHandoverDocumentRequest createHandoverDocumentRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Package packages = packageRepository.findById(packageId).orElseThrow(()-> new AppException(ErrorCode.PACKAGE_NOT_FOUND));

        HandoverDocument handoverDocument = HandoverDocument.builder()
                .handoverNo(genarateHandoverNo())
                .users(users)
                .orders(orders)
                .packages(packages)
                .staff(createHandoverDocumentRequest.getStaff())
                .handoverDescription(createHandoverDocumentRequest.getHandoverDescription())
                .vehicle(createHandoverDocumentRequest.getVehicle())
                .destination(createHandoverDocumentRequest.getDestination())
                .departure(createHandoverDocumentRequest.getDeparture())
                .totalPrice(createHandoverDocumentRequest.getTotalPrice())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        handoverDocumentRepository.save(handoverDocument);
        return convertToHandoverDocumentResponse(handoverDocument);
    }

    public HandoverDocumentResponse updateHandoverDocument(Integer id, UpdateHandoverDocumentRequest updateHandoverDocumentRequest) {
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));

        handoverDocument.setUsers(updateHandoverDocumentRequest.getUsers());
        handoverDocument.setOrders(updateHandoverDocumentRequest.getOrders());
        handoverDocument.setHandoverNo(updateHandoverDocumentRequest.getHandoverNo());
        handoverDocument.setStaff(updateHandoverDocumentRequest.getStaff());
        handoverDocument.setHandoverDescription(updateHandoverDocumentRequest.getHandoverDescription());
        handoverDocument.setVehicle(updateHandoverDocumentRequest.getVehicle());
        handoverDocument.setDestination(updateHandoverDocumentRequest.getDestination());
        handoverDocument.setDeparture(updateHandoverDocumentRequest.getDeparture());
        handoverDocument.setTotalPrice(updateHandoverDocumentRequest.getTotalPrice());

        handoverDocumentRepository.save(handoverDocument);

        return convertToHandoverDocumentResponse(handoverDocument);
    }

    public List<HandoverDocumentResponse> viewAllHandoverDocuments() {
        List<HandoverDocument> handoverDocuments = handoverDocumentRepository.findAll();
        List<HandoverDocumentResponse> handoverDocumentResponses = new ArrayList<>();

        for (HandoverDocument handoverDocument : handoverDocuments) {
            HandoverDocumentResponse response = convertToHandoverDocumentResponse(handoverDocument);
            handoverDocumentResponses.add(response);
        }

        return handoverDocumentResponses;
    }

    public HandoverDocumentResponse deleteHandoverDocument(Integer id) {
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));

        HandoverDocumentResponse response = convertToHandoverDocumentResponse(handoverDocument);
        handoverDocumentRepository.deleteById(id);

        return response;
    }

    public HandoverDocumentResponse getHandoverDocumentById(Integer id) {
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        HandoverDocumentResponse response = convertToHandoverDocumentResponse(handoverDocument);
        handoverDocumentRepository.getHandoverDocumentById(id);
        return convertToHandoverDocumentResponse(handoverDocument);
    }

    private HandoverDocumentResponse convertToHandoverDocumentResponse(HandoverDocument handoverDocument) {
        return HandoverDocumentResponse.builder()
                .userId(handoverDocument.getUsers().getId())
                .orderId(handoverDocument.getOrders().getId())
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
    private String genarateHandoverNo() {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder;
        String handoverNo = "";
        do {
            stringBuilder = new StringBuilder("ORD");
            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(5);
                stringBuilder.append(RANDOM_STRING.charAt(randomIndex));
            }
            handoverNo = stringBuilder.toString();
        } while (orderRepository.existsByOrderCode(handoverNo));
        return handoverNo;
    }
}






    /*@Autowired
    private ModelMapper modelMapper;

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
    public HandoverDocumentDTO getHandoverDocumentById(int id) {
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        return modelMapper.map(handoverDocument, HandoverDocumentDTO.class);
    }

    // Create a new handover document
    public ApiResponse<HandoverDocumentDTO> createHandoverDocument(CreateHandoverDocumentRequest handoverDocumentRequest) {
        // Map the CreateHandoverDocumentRequest to HandoverDocument entity
        HandoverDocument handoverDocument = modelMapper.map(handoverDocumentRequest, HandoverDocument.class);

        // Save the new handover document
        handoverDocument = handoverDocumentRepository.save(handoverDocument);

        // Map the saved document back to HandoverDocumentDTO
        HandoverDocumentDTO createdHandoverDocumentDTO = modelMapper.map(handoverDocument, HandoverDocumentDTO.class);

        // Build and return the ApiResponse with the created document
        return ApiResponse.<HandoverDocumentDTO>builder()
                .code(HttpStatus.CREATED.value()) // HTTP status code for created
                .message("Handover document created successfully")
                .result(createdHandoverDocumentDTO)
                .build();
    }

    // Update an existing handover document by ID
    public ApiResponse<HandoverDocumentDTO> updateHandoverDocument(Integer id, CreateHandoverDocumentRequest handoverDocumentRequest) {
        // Check if the handover document exists
        HandoverDocument existingHandoverDocument = handoverDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));

        // Update the existing document with new values
        modelMapper.map(handoverDocumentRequest, existingHandoverDocument);

        // Save the updated handover document
        existingHandoverDocument = handoverDocumentRepository.save(existingHandoverDocument);

        // Map the saved document back to HandoverDocumentDTO
        HandoverDocumentDTO updatedHandoverDocumentDTO = modelMapper.map(existingHandoverDocument, HandoverDocumentDTO.class);

        // Build and return the ApiResponse with the updated document
        return ApiResponse.<HandoverDocumentDTO>builder()
                .code(HttpStatus.OK.value()) // HTTP status code for success
                .message("Handover document updated successfully")
                .result(updatedHandoverDocumentDTO)
                .build();
    }

    // Delete a handover document by ID
    public ApiResponse<Void> deleteHandoverDocument(Integer id) {
        // Check if the handover document exists
        if (!handoverDocumentRepository.existsById(id)) {
            throw new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND);
        }

        // Delete the document
        handoverDocumentRepository.deleteById(id);

        // Build and return the ApiResponse indicating success
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value()) // HTTP status code for no content (successful deletion)
                .message("Handover document deleted successfully")
                .result(null) // No result for deletion
                .build();
    }
}*/
