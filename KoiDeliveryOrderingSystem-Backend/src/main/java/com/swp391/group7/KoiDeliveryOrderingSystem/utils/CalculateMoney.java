package com.swp391.group7.KoiDeliveryOrderingSystem.utils;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.*;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateMoney {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryMethodRepository deliveryMethodRepository;

    @Autowired
    private HealthServiceCategoryRepository healthServiceCategoryRepository;

    @Autowired
    private HealthServiceOrderRepository healthServiceOrderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public Float calculateMoney(Integer orderId) {
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.PENDING)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        float deliveryMethod = deliveryMethodRepository
                .findByDeliveryMethodNameAndStatus(orders.getDeliveryMethod().getDeliveryMethodName(), SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND)).getPrice();
        double orderDetail = orderDetailRepository.findByOrdersAndStatus(orders, SystemStatusEnum.AVAILABLE)
                .stream().mapToDouble(OrderDetail::getAmount).sum();
        double healthService = healthServiceOrderRepository.findByOrdersAndStatus(orders, SystemStatusEnum.AVAILABLE)
                .stream().mapToDouble((healthServiceOrder1 -> {
                    HealthServiceCategory healthServiceCategory = healthServiceCategoryRepository.findByIdAndStatus(healthServiceOrder1.getHealthServiceCategory().getId(), SystemStatusEnum.AVAILABLE)
                            .orElseThrow(() -> new AppException(ErrorCode.HEALTH_SERVICE_CATEGORY_NOT_FOUND));
                    return healthServiceCategory.getPrice();
                })).sum();
        Double total = (orders.getDistance() * deliveryMethod) + orderDetail + healthService;
        return Float.parseFloat(String.valueOf(total));
    }
}
