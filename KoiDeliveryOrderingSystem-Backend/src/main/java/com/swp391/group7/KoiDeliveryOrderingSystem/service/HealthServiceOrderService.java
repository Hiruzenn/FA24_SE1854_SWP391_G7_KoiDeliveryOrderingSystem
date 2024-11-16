package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceCategory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceOrder;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthserviceorder.CreateHealthServiceOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.healthserviceorder.UpdateHealthServiceOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.HealthServiceOrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HealthServiceCategoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HealthServiceOrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthServiceOrderService {
    @Autowired
    private HealthServiceOrderRepository healthServiceOrderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private HealthServiceCategoryRepository healthServiceCategoryRepository;

    @Autowired
    private AccountUtils accountUtils;

    public HealthServiceOrderResponse createHealthServiceOrder(CreateHealthServiceOrderRequest request) throws AppException {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(request.getOrderId(), OrderStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        HealthServiceCategory healthServiceCategory = healthServiceCategoryRepository.findByIdAndStatus(request.getHealthServiceCategoryId(), SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTH_SERVICE_CATEGORY_NOT_FOUND));
        HealthServiceOrder healthServiceOrder = HealthServiceOrder.builder()
                .orders(orders)
                .healthServiceCategory(healthServiceCategory)
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .build();
        healthServiceOrderRepository.save(healthServiceOrder);
        return convertToHealthServiceOrderResponse(healthServiceOrder);
    }

    public List<HealthServiceOrderResponse> getHealthServiceOrderByOrderId(Integer orderId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<HealthServiceOrderResponse> healthServiceOrderResponses = new ArrayList<>();
        List<HealthServiceOrder> healthServiceOrders = healthServiceOrderRepository.findByOrders(orders);
        for (HealthServiceOrder healthServiceOrder : healthServiceOrders) {
            healthServiceOrderResponses.add(convertToHealthServiceOrderResponse(healthServiceOrder));
        }
        return healthServiceOrderResponses;
    }

    public HealthServiceOrderResponse deleteHealthServiceOrder(Integer id) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        HealthServiceOrder healthServiceOrder = healthServiceOrderRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTH_SERVICE_ORDER_NOT_FOUND));
        healthServiceOrderRepository.delete(healthServiceOrder);
        return convertToHealthServiceOrderResponse(healthServiceOrder);
    }

    public HealthServiceOrderResponse convertToHealthServiceOrderResponse(HealthServiceOrder healthServiceOrder) {
        return HealthServiceOrderResponse.builder()
                .id(healthServiceOrder.getId())
                .orderId(healthServiceOrder.getOrders().getId())
                .healthServiceCategory(healthServiceOrder.getHealthServiceCategory())
                .createAt(healthServiceOrder.getCreateAt())
                .createBy(healthServiceOrder.getCreateBy())
                .build();
    }
}
