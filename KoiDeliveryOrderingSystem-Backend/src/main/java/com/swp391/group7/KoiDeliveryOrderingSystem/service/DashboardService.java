package com.swp391.group7.KoiDeliveryOrderingSystem.service;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.dashboard.CustomerDashboardResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.dashboard.ManagerDashboardResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class DashboardService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private FishProfileRepository fishProfileRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public ManagerDashboardResponse manager() {
        Integer totalUser = userRepository.findAll().stream().toList().size();
        Integer totalOrders = orderRepository.findByStatus(OrderStatusEnum.COMPLETED).stream().toList().size();
        Integer orderInWeek = orderRepository.findByStatusAndCreateAtBetween(OrderStatusEnum.COMPLETED,
                LocalDateTime.now().minusWeeks(1), LocalDateTime.now()).size();
        Integer orderInMonth = orderRepository.findByStatusAndCreateAtBetween(OrderStatusEnum.COMPLETED,
                LocalDateTime.now().minusMonths(1), LocalDateTime.now()).size();
        Double profitInWeek = orderRepository.findByStatusAndCreateAtBetween(OrderStatusEnum.COMPLETED,
                LocalDateTime.now().minusWeeks(1), LocalDateTime.now()).stream().mapToDouble(Orders::getTotalAmount).sum();
        Double profitInMonth = orderRepository.findByStatusAndCreateAtBetween(OrderStatusEnum.COMPLETED,
                LocalDateTime.now().minusMonths(1), LocalDateTime.now()).stream().mapToDouble(Orders::getTotalAmount).sum();
        Double totalProfit = orderRepository.findByStatus(OrderStatusEnum.COMPLETED)
                .stream().mapToDouble(Orders::getTotalAmount).sum();
        Integer totalReport = reportRepository.findAll().size();
        Integer totalFeedback = feedbackRepository.findAll().size();
        return ManagerDashboardResponse.builder()
                .totalUser(totalUser)
                .totalOrder(totalOrders)
                .orderInWeek(orderInWeek)
                .orderInMonth(orderInMonth)
                .profitInWeek(profitInWeek)
                .profitInMonth(profitInMonth)
                .totalProfit(totalProfit)
                .totalReport(totalReport)
                .totalFeedback(totalFeedback)
                .build();
    }

    public CustomerDashboardResponse customer() {
        Users users = accountUtils.getCurrentUser();
        Integer availableOrder = orderRepository.findByUsersAndStatus(users, OrderStatusEnum.AVAILABLE).size();
        Integer inProgressOrder = orderRepository.findByUsersAndStatus(users, OrderStatusEnum.IN_PROGRESS).size();
        Integer completedOrder = orderRepository.findByUsersAndStatus(users, OrderStatusEnum.COMPLETED).size();
        Integer totalFishProfile = fishProfileRepository.findByCreateBy(users.getId()).size();
        Double totalPaid = paymentRepository.findByUsers(users).stream().mapToDouble(Payment::getAmount).sum();
        Integer totalReport = reportRepository.findByUsers(users).size();
        Integer totalFeedback = feedbackRepository.findByUsers(users).size();
        return CustomerDashboardResponse.builder()
                .availableOrder(availableOrder)
                .inProgressOrder(inProgressOrder)
                .completedOrder(completedOrder)
                .totalFishProfile(totalFishProfile)
                .totalPaid(totalPaid)
                .totalReport(totalReport)
                .totalFeedback(totalFeedback)
                .build();
    }
}
