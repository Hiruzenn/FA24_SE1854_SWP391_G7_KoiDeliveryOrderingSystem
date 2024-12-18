package com.swp391.group7.KoiDeliveryOrderingSystem.service;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HandoverStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.PaymentStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.CreateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument.UpdateHandoverDocumentRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HandoverDocumentResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HandoverDocumentService {
    @Autowired
    private HandoverDocumentRepository handoverDocumentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HealthCareDeliveryHistoryRepository healthCareDeliveryHistoryRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    public static final String RANDOM_STRING = "0123456789";

    public HandoverDocumentResponse create(CreateHandoverDocumentRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Users customer = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Orders orders = orderRepository.findByIdAndStatus(request.getOrderId(), OrderStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (handoverDocumentRepository.existsByOrders(orders)) {
            throw new AppException(ErrorCode.HANDOVER_EXISTED);
        }
        HandoverDocument handoverDocument = HandoverDocument.builder()
                .handoverNo(generateHandoverNo())
                .users(customer)
                .orders(orders)
                .handoverDescription(request.getHandoverDescription())
                .vehicle(orders.getDeliveryMethod().getName())
                .destination(orders.getDestination())
                .departure(orders.getDeparture())
                .totalPrice(orders.getTotalAmount())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .handoverStatus(HandoverStatusEnum.PENDING)
                .build();
        handoverDocumentRepository.save(handoverDocument);
        return convertToHandoverDocumentResponse(handoverDocument);
    }

    public HandoverDocumentResponse update(Integer handoverDocumentId, UpdateHandoverDocumentRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(handoverDocumentId)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        Orders orders = orderRepository.findByHandoverDocuments(handoverDocument).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Boolean checkHealthCareDeliveryHistory = healthCareDeliveryHistoryRepository.existsByHandoverDocument(handoverDocument);
        if (!checkHealthCareDeliveryHistory) {
            throw new AppException(ErrorCode.NEED_DELIVERY_HISTORY);
        }

        if (orders.getPaymentStatus().equals(PaymentStatusEnum.UNPAID)) {
            orders.setPaymentStatus(PaymentStatusEnum.PAID);
            Payment payment = Payment.builder()
                    .users(orders.getUsers())
                    .orders(orders)
                    .paymentCode(orders.getOrderCode().substring(3))
                    .paymentDate(LocalDate.now())
                    .paymentStatus(PaymentStatusEnum.PAID)
                    .paymentMethod("CAST")
                    .amount(orders.getTotalAmount())
                    .build();
            paymentRepository.save(payment);
        }
        orders.setStatus(OrderStatusEnum.COMPLETED);
        orders.setReceivingDate(LocalDateTime.now());
        handoverDocument.setHandoverDescription(request.getHandoverDescription());
        handoverDocument.setHandoverStatus(HandoverStatusEnum.COMPLETED);
        handoverDocument.setImage(request.getImage());
        handoverDocument.setUpdateAt(LocalDateTime.now());
        handoverDocument.setUpdateBy(users.getId());
        handoverDocumentRepository.save(handoverDocument);
        orderRepository.save(orders);

        return convertToHandoverDocumentResponse(handoverDocument);
    }

    public void delete(Integer orderId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        HandoverDocument handoverDocument = handoverDocumentRepository.findByOrdersAndHandoverStatus(orders, HandoverStatusEnum.PENDING)
                .orElseThrow(() -> new AppException(ErrorCode.DELETE_HANDOVER_PENDING));
        handoverDocumentRepository.delete(handoverDocument);
    }

    public List<HandoverDocumentResponse> getAll() {
        List<HandoverDocument> handoverDocumentList = handoverDocumentRepository.findAll();
        return convertToListHandoverDocumentResponse(handoverDocumentList);
    }

    public List<HandoverDocumentResponse> viewByDeliveryStaff() {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        List<HandoverDocument> handoverDocumentList = handoverDocumentRepository.findByUsers(users);
        return convertToListHandoverDocumentResponse(handoverDocumentList);
    }

    public HandoverDocumentResponse viewByOrder(Integer orderId) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        HandoverDocument handoverDocument = handoverDocumentRepository.findByOrders(orders)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        return convertToHandoverDocumentResponse(handoverDocument);
    }

    public HandoverDocumentResponse viewOne(Integer id) {
        HandoverDocument handoverDocument = handoverDocumentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HANDOVER_DOCUMENT_NOT_FOUND));
        return convertToHandoverDocumentResponse(handoverDocument);
    }

    public List<HandoverDocumentResponse> convertToListHandoverDocumentResponse(List<HandoverDocument> handoverDocumentList) {
        List<HandoverDocumentResponse> handoverDocumentResponses = new ArrayList<>();
        for (HandoverDocument handoverDocument : handoverDocumentList) {
            handoverDocumentResponses.add(convertToHandoverDocumentResponse(handoverDocument));
        }
        return handoverDocumentResponses;
    }

    public HandoverDocumentResponse convertToHandoverDocumentResponse(HandoverDocument handoverDocument) {
        return HandoverDocumentResponse.builder()
                .id(handoverDocument.getId())
                .handoverNo(handoverDocument.getHandoverNo())
                .userId(handoverDocument.getUsers().getId())
                .orderId(handoverDocument.getOrders().getId())
                .handoverDescription(handoverDocument.getHandoverDescription())
                .vehicle(handoverDocument.getVehicle())
                .destination(handoverDocument.getDestination())
                .departure(handoverDocument.getDeparture())
                .totalPrice(handoverDocument.getTotalPrice())
                .image(handoverDocument.getImage())
                .createAt(handoverDocument.getCreateAt())
                .createBy(handoverDocument.getCreateBy())
                .updateAt(handoverDocument.getUpdateAt())
                .updateBy(handoverDocument.getUpdateBy())
                .handoverStatusEnum(handoverDocument.getHandoverStatus())
                .build();
    }

    private String generateHandoverNo() {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder;
        String handoverNo = "";
        do {
            stringBuilder = new StringBuilder("HDN");
            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(5);
                stringBuilder.append(RANDOM_STRING.charAt(randomIndex));
            }
            handoverNo = stringBuilder.toString();
        } while (orderRepository.existsByOrderCode(handoverNo));
        return handoverNo;
    }
}
