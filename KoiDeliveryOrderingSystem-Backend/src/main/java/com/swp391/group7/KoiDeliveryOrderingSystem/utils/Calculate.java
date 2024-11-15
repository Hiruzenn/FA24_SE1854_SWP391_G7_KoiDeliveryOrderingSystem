package com.swp391.group7.KoiDeliveryOrderingSystem.utils;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Calculate {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryMethodRepository deliveryMethodRepository;

    @Autowired
    private HealthServiceCategoryRepository healthServiceCategoryRepository;

    @Autowired
    private HealthServiceOrderRepository healthServiceOrderRepository;

    private final Map<String, Float> fixedDistance = new HashMap<>();
    @Autowired
    private FishProfileRepository fishProfileRepository;
    @Autowired
    private FishCategoryRepository fishCategoryRepository;

    public Float calculateMoney(Integer orderId) {
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        float deliveryMethod = deliveryMethodRepository
                .findByNameAndStatus(orders.getDeliveryMethod().getName(), SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND)).getPrice();
        double healthService = healthServiceOrderRepository.findByOrdersAndStatus(orders, SystemStatusEnum.AVAILABLE)
                .stream().mapToDouble((healthServiceOrder1 -> {
                    HealthServiceCategory healthServiceCategory = healthServiceCategoryRepository.findByIdAndStatus(healthServiceOrder1.getHealthServiceCategory().getId(), SystemStatusEnum.AVAILABLE)
                            .orElseThrow(() -> new AppException(ErrorCode.HEALTH_SERVICE_CATEGORY_NOT_FOUND));
                    return healthServiceCategory.getPrice();
                })).sum();
        double fishProfile = fishProfileRepository.findByOrders(orders).stream().mapToDouble(fishProfileList -> {
            FishCategory fishCategory = fishCategoryRepository.findByNameAndStatus(fishProfileList.getFishCategory().getName(), SystemStatusEnum.AVAILABLE)
                    .orElseThrow(() -> new AppException(ErrorCode.FISH_CATEGORY_NOT_FOUND));
            return fishCategory.getPrice();
        }).sum();
        Double total = (orders.getDistance() * deliveryMethod) + healthService + fishProfile;
        return Float.parseFloat(String.valueOf(total));
    }

    public Calculate() {
        //Trong Việt Nam
        fixedDistance.put("Hồ Chí Minh - Hà Nội", 1500f);
        fixedDistance.put("Hồ Chí Minh - Đà Nẵng", 800f);
        fixedDistance.put("Hà Nội - Đà Nẵng", 750f);
        fixedDistance.put("Hà Nội - Hồ Chí Minh", 1500f);
        fixedDistance.put("Đà Nẵng - Hồ Chí Minh", 800f);
        fixedDistance.put("Đà Nẵng - Hà Nội", 750f);

        //Từ Nhật Về Việt Nam
        fixedDistance.put("Tokyo - Hồ Chí Minh", 4400f);
        fixedDistance.put("Tokyo - Hà Nội", 3700f);
        fixedDistance.put("Osaka - Hồ Chí Minh", 4000f);
        fixedDistance.put("Osaka - Hà Nội", 3300f);

    }

    public Float calculateDistance(String start, String end) {
        String key = getCity(start) + " - " + getCity(end);
        return fixedDistance.getOrDefault(key, 0f);
    }

    public String getCity(String location) {
        if (location.contains("Hồ Chí Minh")) return "Hồ Chí Minh";
        if (location.contains("Hà Nội")) return "Hà Nội";
        if (location.contains("Đà Nẵng")) return "Đà Nẵng";
        if (location.contains("Tokyo")) return "Tokyo";
        if (location.contains("Osaka")) return "Osaka";
        return location;
    }

}
