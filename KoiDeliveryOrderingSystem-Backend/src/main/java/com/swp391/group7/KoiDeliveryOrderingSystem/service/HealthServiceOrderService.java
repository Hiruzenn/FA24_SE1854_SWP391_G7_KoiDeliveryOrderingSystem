package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Customers;
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
        Customers customers = accountUtils.getCurrentCustomer();
        if (customers == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        HealthServiceCategory healthServiceCategory = healthServiceCategoryRepository.findById(heathServiceCategoryId)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTH_CHECK_FAILED));
        HealthServiceOrder healthServiceOrder = HealthServiceOrder.builder()
                .orders(orders)
                .healthServiceCategory(healthServiceCategory)
                .createAt(LocalDateTime.now())
                .createBy(customers.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(customers.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        healthServiceOrderRepository.save(healthServiceOrder);
        return convertToHealthServiceOrderResponse(healthServiceOrder);
    }


    public HealthServiceOrderResponse convertToHealthServiceOrderResponse(HealthServiceOrder healthServiceOrder) {
        return HealthServiceOrderResponse.builder()
                .orders(healthServiceOrder.getOrders())
                .healthServiceCategory(healthServiceOrder.getHealthServiceCategory())
                .createAt(healthServiceOrder.getCreateAt())
                .createBy(healthServiceOrder.getCreateBy())
                .updateAt(healthServiceOrder.getUpdateAt())
                .updateBy(healthServiceOrder.getUpdateBy())
                .status(healthServiceOrder.getStatus())
                .build();
    }
}
