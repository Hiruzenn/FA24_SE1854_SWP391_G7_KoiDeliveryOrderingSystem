package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceCategory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceOrder;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
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

    public HealthServiceOrderResponse createHealthServiceOrder(Integer orderId, Integer heathServiceCategoryId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        HealthServiceCategory healthServiceCategory = healthServiceCategoryRepository.findById(heathServiceCategoryId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        if (healthServiceOrderRepository.existsHealthServiceOrderByHealthServiceCategoryAndOrdersAndStatus(healthServiceCategory, orders, SystemStatusEnum.AVAILABLE)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        HealthServiceOrder healthServiceOrder = HealthServiceOrder.builder()
                .orders(orders)
                .healthServiceCategory(healthServiceCategory)
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        healthServiceOrderRepository.save(healthServiceOrder);
        return convertToHealthServiceOrderResponse(healthServiceOrder);
    }

    public List<HealthServiceOrderResponse> getHealthServiceOrderByOrderId(Integer orderId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(()-> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<HealthServiceOrderResponse> healthServiceOrderResponses = new ArrayList<>();
        List<HealthServiceOrder> healthServiceOrders = healthServiceOrderRepository.findHealthServiceOrderByOrders(orders);
        for (HealthServiceOrder healthServiceOrder : healthServiceOrders) {
            if (healthServiceOrder.getStatus() == SystemStatusEnum.AVAILABLE) {
                healthServiceOrderResponses.add(convertToHealthServiceOrderResponse(healthServiceOrder));
            }
        }
        return healthServiceOrderResponses;
    }
    public void deleteHealthServiceOrder(Integer orderId, Integer heathServiceCategoryId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        HealthServiceCategory healthServiceCategory = healthServiceCategoryRepository.findById(heathServiceCategoryId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        HealthServiceOrder healthServiceOrder = healthServiceOrderRepository
                .findHealthServiceOrderByHealthServiceCategoryAndOrdersAndStatus(healthServiceCategory, orders, SystemStatusEnum.AVAILABLE);
        healthServiceOrder.setStatus(SystemStatusEnum.NOT_AVAILABLE);
    }
    public HealthServiceOrderResponse convertToHealthServiceOrderResponse(HealthServiceOrder healthServiceOrder) {
        return HealthServiceOrderResponse.builder()
                .id(healthServiceOrder.getId())
                .orderId(healthServiceOrder.getOrders().getId())
                .healthServiceCategoryId(healthServiceOrder.getHealthServiceCategory().getId())
                .createAt(healthServiceOrder.getCreateAt())
                .createBy(healthServiceOrder.getCreateBy())
                .updateAt(healthServiceOrder.getUpdateAt())
                .updateBy(healthServiceOrder.getUpdateBy())
                .status(healthServiceOrder.getStatus())
                .build();
    }
}
