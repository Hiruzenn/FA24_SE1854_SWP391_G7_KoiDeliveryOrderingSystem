package com.swp391.group7.KoiDeliveryOrderingSystem.service;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.DashboardResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public DashboardResponse dashboard() {
        Integer totalUser = userRepository.findAll().stream().toList().size();
        Integer totalOrders = orderRepository.findByStatus(SystemStatusEnum.AVAILABLE).stream().toList().size();
        Integer orderInWeek = orderRepository.findByStatusAndCreateAtBetween(SystemStatusEnum.AVAILABLE,
                LocalDateTime.now().minusWeeks(1), LocalDateTime.now()).size();
        Integer orderInMonth = orderRepository.findByStatusAndCreateAtBetween(SystemStatusEnum.AVAILABLE,
                LocalDateTime.now().minusMonths(1), LocalDateTime.now()).size();
        Double profitInWeek = orderRepository.findByStatusAndCreateAtBetween(SystemStatusEnum.AVAILABLE,
                LocalDateTime.now().minusWeeks(1), LocalDateTime.now()).stream().mapToDouble(Orders::getTotalAmount).sum();
        Double profitInMonth = orderRepository.findByStatusAndCreateAtBetween(SystemStatusEnum.AVAILABLE,
                LocalDateTime.now().minusMonths(1), LocalDateTime.now()).stream().mapToDouble(Orders::getTotalAmount).sum();
        Double totalProfit = orderRepository.findByStatus(SystemStatusEnum.AVAILABLE)
                .stream().mapToDouble(Orders::getTotalAmount).sum();
        return DashboardResponse.builder()
                .totalUser(totalUser)
                .totalOrder(totalOrders)
                .orderInWeek(orderInWeek)
                .orderInMonth(orderInMonth)
                .profitInWeek(profitInWeek)
                .profitInMonth(profitInMonth)
                .totalProfit(totalProfit)
                .build();
    }
}
